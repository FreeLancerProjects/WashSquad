package com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.creative.share.apps.wash_squad_driver.R;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.activity.HomeActivity;
import com.creative.share.apps.wash_squad_driver.databinding.FragmentProfileBinding;
import com.creative.share.apps.wash_squad_driver.interfaces.Listeners;
import com.creative.share.apps.wash_squad_driver.models.Edit_Profile_Model;
import com.creative.share.apps.wash_squad_driver.models.LoginModel;
import com.creative.share.apps.wash_squad_driver.models.UserModel;
import com.creative.share.apps.wash_squad_driver.preferences.Preferences;
import com.creative.share.apps.wash_squad_driver.remote.Api;
import com.creative.share.apps.wash_squad_driver.share.Common;
import com.creative.share.apps.wash_squad_driver.tags.Tags;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Profile extends Fragment implements Listeners.ShowCountryDialogListener, OnCountryPickerListener,Listeners.EditProfileListener {

    private HomeActivity activity;
    private FragmentProfileBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private CountryPicker countryPicker;
    private String lang;
    private String code;
private Edit_Profile_Model edit_profile_model;

    public static Fragment_Profile newInstance() {

        return new Fragment_Profile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setShowCountryListener(this);

        activity = (HomeActivity) getActivity();
        preferences = Preferences.newInstance();

        userModel = preferences.getUserData(activity);
        edit_profile_model=new Edit_Profile_Model(userModel.getFull_name(),userModel.getPhone_code(),userModel.getPhone());
        binding.setEditprofilemodel(edit_profile_model);
        binding.edtName.setText(userModel.getFull_name());
        binding.tvCode.setText(userModel.getPhone_code().replaceFirst("00", "+"));
        binding.edtPhone.setText(userModel.getPhone());
        binding.setEditprofilelistener(this);
        code = userModel.getPhone_code();

        createCountryDialog();

    }

    private void createCountryDialog() {
        countryPicker = new CountryPicker.Builder()
                .canSearch(true)
                .listener(this)
                .theme(CountryPicker.THEME_NEW)
                .with(activity)
                .build();


        code = userModel.getPhone_code();


    }

    @Override
    public void showDialog() {

        countryPicker.showDialog(activity);
    }

    @Override
    public void onSelectCountry(Country country) {

        updatePhoneCode(country);
    }

    private void updatePhoneCode(Country country) {
        code = country.getDialCode();
        binding.tvCode.setText(country.getDialCode());

    }

    @Override
    public void checkDataEditProfile(String name, String phone_code, String phone) {
        if (phone.startsWith("0"))
        {
            phone = phone.replaceFirst("0","");
        }
        edit_profile_model = new Edit_Profile_Model(name,phone_code,phone);
        binding.setEditprofilelistener(this);

        if (edit_profile_model.isDataValid(activity))
        {
            Editprofile(name,phone_code,phone);
        }
    }
    private void Editprofile(String name, String phone_code, String phone)
    {
        ProgressDialog dialog = Common.createProgressDialog(activity,getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        try {

            Api.getService(Tags.base_url)
                    .edit_profile(name,phone_code,phone)
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            dialog.dismiss();
                            if (response.isSuccessful()&&response.body()!=null)
                            {
                                preferences.create_update_userData(activity,response.body());
                                Toast.makeText(activity, getString(R.string.suc), Toast.LENGTH_SHORT).show();


                            }else
                            {
                                try {

                                    Log.e("error",response.code()+"_"+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (response.code() == 422) {
                                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                } else if (response.code()==403)
                                {
                                    Toast.makeText(activity, R.string.user_not_active, Toast.LENGTH_SHORT).show();

                                }else if (response.code()==404)
                                {
                                    Toast.makeText(activity, R.string.inc_phone_pas, Toast.LENGTH_SHORT).show();

                                }else if (response.code()==405)
                                {
                                    Toast.makeText(activity, R.string.not_active_phone, Toast.LENGTH_SHORT).show();

                                }else if (response.code() == 500) {
                                    Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();

                                }else
                                {
                                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();


                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage()!=null)
                                {
                                    Log.e("error",t.getMessage());
                                    if (t.getMessage().toLowerCase().contains("failed to connect")||t.getMessage().toLowerCase().contains("unable to resolve host"))
                                    {
                                        Toast.makeText(activity,R.string.something, Toast.LENGTH_SHORT).show();
                                    }else
                                    {
                                        Toast.makeText(activity,t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }catch (Exception e){}
                        }
                    });
        }catch (Exception e){
            dialog.dismiss();

        }
    }
}
