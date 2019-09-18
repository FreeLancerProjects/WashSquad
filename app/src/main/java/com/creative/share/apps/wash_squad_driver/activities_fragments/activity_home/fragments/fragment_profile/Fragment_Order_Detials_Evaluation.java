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
import com.creative.share.apps.wash_squad_driver.models.Order_Data_Model;
import com.creative.share.apps.wash_squad_driver.models.UserModel;
import com.creative.share.apps.wash_squad_driver.preferences.Preferences;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_Order_Detials_Evaluation extends Fragment {
    final static private String Tag = "order_detials";
    private Order_Data_Model.OrderModel orderModel;

    private HomeActivity activity;
    private FragmentOrderDetialsEvaluationBinding binding;
    private Preferences preferences;
    private UserModel userModel;
    private String lang;

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
        binding.setLang(lang);
        binding.setOrderModel(orderModel);

    }


}
