package com.creative.share.apps.wash_squad.activities_fragments.activity_order_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.creative.share.apps.wash_squad.R;
import com.creative.share.apps.wash_squad.activities_fragments.activity_order_details.fragments.Fragment_Product_Details;
import com.creative.share.apps.wash_squad.activities_fragments.activity_order_details.fragments.Fragment_Order_Products;
import com.creative.share.apps.wash_squad.adapters.ViewPagerAdapter;
import com.creative.share.apps.wash_squad.databinding.ActivityOrderDetailsBinding;
import com.creative.share.apps.wash_squad.language.LanguageHelper;
import com.creative.share.apps.wash_squad.models.Order_Data_Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class OrderDetailsActivity extends AppCompatActivity {
    private ActivityOrderDetailsBinding binding;
    private Order_Data_Model.OrderModel orderModel;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> title;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        getDataFromIntent();
        initView();




    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            orderModel = (Order_Data_Model.OrderModel) intent.getSerializableExtra("data");
        }
    }

    private void initView() {

        Paper.init(this);
        lang  = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setModel(orderModel);
        fragmentList = new ArrayList<>();
        title = new ArrayList<>();
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(2);

        fragmentList.add(Fragment_Order_Products.newInstance(orderModel));
        fragmentList.add(Fragment_Product_Details.newInstance(orderModel));
        title.add(getString(R.string.products));
        title.add(getString(R.string.info));
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(fragmentList);
        adapter.addTitles(title);
        binding.pager.setAdapter(adapter);

        for (int i =0;i<binding.tab.getChildCount();i++)
        {
            View view =  ((ViewGroup)binding.tab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams  params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(10,0,10,0);
        }
    }

}
