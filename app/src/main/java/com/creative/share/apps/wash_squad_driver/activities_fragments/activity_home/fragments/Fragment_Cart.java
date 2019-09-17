package com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.creative.share.apps.wash_squad_driver.R;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.activity.HomeActivity;
import com.creative.share.apps.wash_squad_driver.adapters.CartAdapterAdapter;
import com.creative.share.apps.wash_squad_driver.databinding.FragmentCartBinding;
import com.creative.share.apps.wash_squad_driver.models.ItemToUpload;
import com.creative.share.apps.wash_squad_driver.models.UserModel;
import com.creative.share.apps.wash_squad_driver.preferences.Preferences;
import com.creative.share.apps.wash_squad_driver.singleton.SingleTon;

public class Fragment_Cart extends Fragment {

    private HomeActivity activity;
    private FragmentCartBinding binding;
    private Preferences preferences;
    private LinearLayoutManager manager;
    private UserModel userModel;
    private CartAdapterAdapter adapter;
    private SingleTon singleTon;

    public static Fragment_Cart newInstance() {
        return new Fragment_Cart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(activity);
        singleTon = SingleTon.newInstance();

        manager = new LinearLayoutManager(activity);
        binding.recView.setLayoutManager(manager);
        adapter = new CartAdapterAdapter(singleTon.getData(),activity,this);
        binding.recView.setAdapter(adapter);
        if (singleTon.getItemsCount()>0)
        {
            binding.ll.setVisibility(View.GONE);
            binding.btnOtherOrder.setVisibility(View.VISIBLE);


        }else
            {
                binding.btnOtherOrder.setVisibility(View.GONE);

                binding.ll.setVisibility(View.VISIBLE);
            }
        binding.btnOrderNow.setOnClickListener(view -> activity.DisplayFragmentMain());
        binding.btnOtherOrder.setOnClickListener(view -> activity.DisplayFragmentMain());

    }

    public void setItemDataToUpload(ItemToUpload itemToUpload) {


    }
}
