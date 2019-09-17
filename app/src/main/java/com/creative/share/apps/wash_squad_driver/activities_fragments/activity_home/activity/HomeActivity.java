package com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.creative.share.apps.wash_squad_driver.R;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_help.HelpActivity;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments.Fragment_Cart;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments.Fragment_Home;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments.Fragment_Main;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments.Fragment_Offer;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments.Fragment_Profile;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_sign_in.SignInActivity;
import com.creative.share.apps.wash_squad_driver.databinding.ActivityHomeBinding;
import com.creative.share.apps.wash_squad_driver.language.LanguageHelper;
import com.creative.share.apps.wash_squad_driver.models.UserModel;
import com.creative.share.apps.wash_squad_driver.preferences.Preferences;
import com.creative.share.apps.wash_squad_driver.singleton.SingleTon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private int fragment_count = 0;
    private Fragment_Home fragment_home;
    private Fragment_Main fragment_main;
    private Fragment_Cart fragment_cart;
    private Fragment_Profile fragment_profile;
    private Fragment_Offer fragment_offer;

    private Preferences preferences;
    private UserModel userModel;
    private SingleTon singleTon;

    @Override
    protected void onResume() {
        super.onResume();
        int cart_count = singleTon.getItemsCount();
        updateCount(cart_count);
    }

    public void updateCount(int cart_count)
    {
        if (fragment_home!=null&&fragment_home.isAdded())
        {
            fragment_home.setNotificationCartCount(cart_count);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase,Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        initView();

        if (savedInstanceState == null) {
            DisplayFragmentHome();
            DisplayFragmentMain();

        }

        binding.imageHelp.setOnClickListener(view -> {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivityForResult(intent,11);

        });
    }


    private void initView() {
        singleTon = SingleTon.newInstance();
        Paper.init(this);
        fragmentManager = this.getSupportFragmentManager();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        String lastVisit = preferences.getLastVisit(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        String now = dateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));

        if (!lastVisit.equals(now))
        {
            updateVisit(now,(Calendar.getInstance().getTimeInMillis()/1000));

        }

    }

    private void updateVisit(String now, long time) {
        /*Api.getService(Tags.base_url)
                .updateVisit(time,2)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                        {
                            preferences.setLastVisit(HomeActivity.this,now);
                        }else
                        {
                            try {
                                Log.e("errorVisitCode",response.code()+"_"+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        try {
                            Log.e("Error",t.getMessage()+"_");
                        }catch (Exception e){}
                    }
                });*/
    }

    public void DisplayFragmentHome() {

        try {
            fragment_count += 1;
            if (fragment_home == null) {
                fragment_home = Fragment_Home.newInstance();
            }

            if (fragment_home.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_home).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_app_container, fragment_home, "fragment_home").addToBackStack("fragment_home").commit();

            }
        }catch (Exception e){}

    }

    public void DisplayFragmentMain()
    {
        try {
            if (fragment_main == null) {
                fragment_main = Fragment_Main.newInstance();
            }
            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }
            if (fragment_offer != null && fragment_offer.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_offer).commit();
            }
            if (fragment_cart != null && fragment_cart.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_cart).commit();
            }
            if (fragment_main.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_main).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_main, "fragment_main").addToBackStack("fragment_main").commit();

            }

            binding.setTitle(getString(R.string.services));
            binding.imageHelp.setVisibility(View.GONE);
            if (fragment_home != null && fragment_home.isAdded()) {
                fragment_home.updateBottomNavigationPosition(1);
                Log.e("ddd","fff");
            }
        }catch (Exception e){}



    }

    public void DisplayFragmentProfile()
    {
        try {
            if (fragment_profile == null) {
                fragment_profile = Fragment_Profile.newInstance();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }
            if (fragment_offer != null && fragment_offer.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_offer).commit();
            }
            if (fragment_cart != null && fragment_cart.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_cart).commit();
            }
            if (fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_profile).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_profile, "fragment_profile").addToBackStack("fragment_profile").commit();

            }
            binding.setTitle(getString(R.string.profile));
            binding.imageHelp.setVisibility(View.VISIBLE);
            if (fragment_home != null && fragment_home.isAdded()) {
                fragment_home.updateBottomNavigationPosition(0);
            }
        }catch (Exception e){}



    }

    public void DisplayFragmentOffer()
    {
        try {
            if (fragment_offer == null) {
                fragment_offer = Fragment_Offer.newInstance();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }
            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }
            if (fragment_cart != null && fragment_cart.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_cart).commit();
            }
            if (fragment_offer.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_offer).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_offer, "fragment_offer").addToBackStack("fragment_offer").commit();

            }
            binding.setTitle(getString(R.string.offer));
            binding.imageHelp.setVisibility(View.GONE);
            if (fragment_home != null && fragment_home.isAdded()) {
                fragment_home.updateBottomNavigationPosition(2);
            }
        }catch (Exception e){}



    }

    public void DisplayFragmentCart()
    {
        try {
            if (fragment_cart == null) {
                fragment_cart = Fragment_Cart.newInstance();
            }
            if (fragment_main != null && fragment_main.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_main).commit();
            }
            if (fragment_profile != null && fragment_profile.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_profile).commit();
            }
            if (fragment_offer != null && fragment_offer.isAdded()) {
                fragmentManager.beginTransaction().hide(fragment_offer).commit();
            }
            if (fragment_cart.isAdded()) {
                fragmentManager.beginTransaction().show(fragment_cart).commit();

            } else {
                fragmentManager.beginTransaction().add(R.id.fragment_home_container, fragment_cart, "fragment_cart").addToBackStack("fragment_cart").commit();

            }
            binding.setTitle(getString(R.string.cart));
            binding.imageHelp.setVisibility(View.GONE);
            if (fragment_home != null && fragment_home.isAdded()) {
                fragment_home.updateBottomNavigationPosition(3);
            }
        }catch (Exception e){}



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==11&&resultCode==RESULT_OK&&data!=null)
        {
            if (data.hasExtra("lang"))
            {
                String lang = data.getStringExtra("lang");
                new Handler()
                        .postDelayed(() -> refreshActivity(lang),1000);

            }
        }
    }

    private void refreshActivity(String lang) {
        preferences.selectedLanguage(this, lang);
        Paper.book().write("lang", lang);
        LanguageHelper.setNewLocale(this, lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (fragment_count>1)
        {
            super.onBackPressed();
            fragment_count--;

        }else
            {
                if (fragment_main!=null&&fragment_main.isAdded()&&fragment_main.isVisible())
                {
                    if (userModel==null)
                    {
                        navigateToSinInActivity();
                    }else
                    {
                        finish();
                    }
                }else
                    {
                        DisplayFragmentMain();
                    }

            }
    }

    private void navigateToSinInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
