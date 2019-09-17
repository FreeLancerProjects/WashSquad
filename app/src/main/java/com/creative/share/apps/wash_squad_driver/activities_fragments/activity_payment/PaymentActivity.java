package com.creative.share.apps.wash_squad_driver.activities_fragments.activity_payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.creative.share.apps.wash_squad_driver.R;
import com.creative.share.apps.wash_squad_driver.adapters.AdditionalAdapter;
import com.creative.share.apps.wash_squad_driver.databinding.ActivityPaymentBinding;
import com.creative.share.apps.wash_squad_driver.interfaces.Listeners;
import com.creative.share.apps.wash_squad_driver.language.LanguageHelper;
import com.creative.share.apps.wash_squad_driver.models.ItemToUpload;
import com.creative.share.apps.wash_squad_driver.singleton.SingleTon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class PaymentActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityPaymentBinding binding;
    private String lang;
    private ItemToUpload itemToUpload;
    private SingleTon singleTon;
    private LinearLayoutManager manager;
    private AdditionalAdapter adapter;
    private double total=0.0;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            itemToUpload = (ItemToUpload) intent.getSerializableExtra("item");
        }
    }


    private void initView() {
        singleTon = SingleTon.newInstance();
        binding.setItemModel(itemToUpload);
        total +=itemToUpload.getService_price();
        itemToUpload.setTotal_price(total);
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd/MMM",Locale.ENGLISH);
        String m_date = dateFormat.format(new Date(itemToUpload.getOrder_date()));
        binding.tvDate.setText(String.format("%s %s %s",m_date,itemToUpload.getTime(),itemToUpload.getTime_type()));




        if (itemToUpload.getSub_services().size()>0)
        {
            manager = new LinearLayoutManager(this);
            adapter = new AdditionalAdapter(itemToUpload.getSub_services(),this);
            binding.recView.setLayoutManager(manager);
            binding.recView.setAdapter(adapter);

            total += getAdditionalServicePrice(itemToUpload.getSub_services());
            itemToUpload.setTotal_price(total);
            binding.tvNoAdditionalServices.setVisibility(View.GONE);
        }else
            {
                binding.tvNoAdditionalServices.setVisibility(View.VISIBLE);

            }
        binding.rb1.setOnClickListener(view -> itemToUpload.setPayment_method(1));
        binding.rb2.setOnClickListener(view -> itemToUpload.setPayment_method(2));

        binding.btnSend.setOnClickListener(view -> {
            if (itemToUpload.isDataValidStep2(this))
            {

            }
        });

        binding.btnOther.setOnClickListener(view -> {
            if (itemToUpload.isDataValidStep2(this))
            {
                singleTon.addItem(itemToUpload);
                Intent intent = getIntent();
                if (intent!=null)
                {
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });

    }

    private double getAdditionalServicePrice(List<ItemToUpload.SubServiceModel> sub_services) {
        double total = 0.0;
        for (ItemToUpload.SubServiceModel subServiceModel:sub_services)
        {
            total +=subServiceModel.getPrice();
        }
        return total;

    }


    @Override
    public void back() {
        finish();
    }

}
