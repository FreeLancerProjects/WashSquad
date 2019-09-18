package com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments.fragment_profile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.creative.share.apps.wash_squad_driver.R;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.activity.HomeActivity;

import com.creative.share.apps.wash_squad_driver.databinding.FragmentOrderDetialsEvaluationBinding;
import com.creative.share.apps.wash_squad_driver.interfaces.Listeners;
import com.creative.share.apps.wash_squad_driver.models.EditProfileModel;
import com.creative.share.apps.wash_squad_driver.models.Order_Data_Model;
import com.creative.share.apps.wash_squad_driver.models.Rating_Order_Model;
import com.creative.share.apps.wash_squad_driver.models.UserModel;
import com.creative.share.apps.wash_squad_driver.preferences.Preferences;
import com.creative.share.apps.wash_squad_driver.remote.Api;
import com.creative.share.apps.wash_squad_driver.share.Common;
import com.creative.share.apps.wash_squad_driver.tags.Tags;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.io.IOException;
import java.util.Locale;

import io.paperdb.Paper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Order_Detials_Evaluation extends Fragment implements Listeners.RatingListener {
    final static private String Tag = "order_detials";
    private Order_Data_Model.OrderModel orderModel;
    private Rating_Order_Model rating_order_model;

    private HomeActivity activity;
    private FragmentOrderDetialsEvaluationBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;
    private float rate = 0.0f;

    public static Fragment_Order_Detials_Evaluation newInstance(Order_Data_Model.OrderModel orderModel) {

        Fragment_Order_Detials_Evaluation fragment_order_detials_evaluation = new Fragment_Order_Detials_Evaluation();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Tag, orderModel);

        fragment_order_detials_evaluation.setArguments(bundle);

        return fragment_order_detials_evaluation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detials_evaluation, container, false);
        initView();

        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.newInstance();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        userModel = preferences.getUserData(activity);
        orderModel = (Order_Data_Model.OrderModel) getArguments().getSerializable(Tag);
        rating_order_model = new Rating_Order_Model();

        binding.setLang(lang);
        binding.setOrderModel(orderModel);
        binding.setRatingOrderModel(rating_order_model);
        binding.setRatelistner(this);

        binding.rateBar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                rate = rating;
            }
        });
    }


    @Override
    public void checkDataRating(String desc) {
        rating_order_model = new Rating_Order_Model(desc, rate);
        binding.setRatelistner(this);
        if (rating_order_model.isDataValid(activity)) {
            if(orderModel.getStatus()==3){
             rateorder(desc,rate);}
            else {
                Toast.makeText(activity,activity.getResources().getString(R.string.order_not_finished),Toast.LENGTH_LONG).show();
            }
        }
        else {
            binding.edtEvalute.setError(getString(R.string.field_req));

        }
    }

    private void rateorder(String desc, float rate) {

        ProgressDialog dialog = Common.createProgressDialog(activity, activity.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();



        Api.getService(Tags.base_url)
                .rateorder(orderModel.getId()+"", desc, rate)
                .enqueue(new Callback<Order_Data_Model.OrderModel>() {
                    @Override
                    public void onResponse(Call<Order_Data_Model.OrderModel> call, Response<Order_Data_Model.OrderModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            //listener.onSuccess(response.body());

                            Toast.makeText(activity, getString(R.string.suc), Toast.LENGTH_SHORT).show();
                            activity.updateprofile();
                            activity.DisplayFragmentOrderDetials(response.body());

                            //update(response.body());

                        } else {
                            Log.e("code", response.code() + "_");
                            try {
                                Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                                Log.e("respons", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // listener.onFailed(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Order_Data_Model.OrderModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                        }
                    }
                });
    }
}
