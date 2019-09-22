package com.creative.share.apps.wash_squad.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.creative.share.apps.wash_squad.R;
import com.creative.share.apps.wash_squad.activities_fragments.activity_home.activity.HomeActivity;
import com.creative.share.apps.wash_squad.databinding.FragmentHomeBinding;
import com.creative.share.apps.wash_squad.models.UserModel;
import com.creative.share.apps.wash_squad.preferences.Preferences;
import com.creative.share.apps.wash_squad.share.Common;

import io.paperdb.Paper;

public class Fragment_Home extends Fragment {
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private Preferences preferences;
    private UserModel userModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);


        setUpBottomNavigation();
        binding.ahBottomNav.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:

                    if (userModel!=null)
                    {
                        activity.DisplayFragmentProfile();

                    }else
                    {
                        Common.CreateNoSignAlertDialog(activity);
                    }
                    break;
                case 1:

                    activity.DisplayFragmentMain();



                    break;
                case 2:
                    activity.DisplayFragmentOffer();


                    break;
                case 3:
                    activity.DisplayFragmentCart();
                    break;

            }
            return false;
        });

    }

    private void setUpBottomNavigation() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.profile), R.drawable.ic_user);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.notifications), R.drawable.ic_car);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.offer), R.drawable.ic_offer);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.ic_nav_cart);

        binding.ahBottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        binding.ahBottomNav.setDefaultBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        binding.ahBottomNav.setTitleTextSizeInSp(14, 12);
        binding.ahBottomNav.setForceTint(true);
        binding.ahBottomNav.setAccentColor(ContextCompat.getColor(activity, R.color.color_second));
        binding.ahBottomNav.setInactiveColor(ContextCompat.getColor(activity, R.color.gray5));

        binding.ahBottomNav.addItem(item1);
        binding.ahBottomNav.addItem(item2);
        binding.ahBottomNav.addItem(item3);
        binding.ahBottomNav.addItem(item4);

        binding.ahBottomNav.setCurrentItem(1);




    }

    public void updateBottomNavigationPosition(int pos) {

        binding.ahBottomNav.setCurrentItem(pos, false);
    }

    public void setNotificationCartCount(int count)
    {
        if (count>0)
        {
            AHNotification ahNotification = new AHNotification.Builder()
                    .setBackgroundColor(ContextCompat.getColor(activity,R.color.colorPrimary))
                    .setText(String.valueOf(count))
                    .setBackgroundColor(ContextCompat.getColor(activity,R.color.red))
                    .build();
            binding.ahBottomNav.setNotification(ahNotification,3);
        }else
            {
                AHNotification ahNotification = new AHNotification.Builder()
                        .setBackgroundColor(ContextCompat.getColor(activity,R.color.colorPrimary))
                        .setText("")
                        .setBackgroundColor(ContextCompat.getColor(activity,R.color.red))
                        .build();
                binding.ahBottomNav.setNotification(ahNotification,3);
            }


    }

    public static Fragment_Home newInstance() {
        return new Fragment_Home();
    }

}
