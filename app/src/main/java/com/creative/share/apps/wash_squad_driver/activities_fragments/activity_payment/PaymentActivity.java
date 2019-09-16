package com.creative.share.apps.wash_squad_driver.activities_fragments.activity_payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.creative.share.apps.wash_squad_driver.R;
import com.creative.share.apps.wash_squad_driver.databinding.ActivityPaymentBinding;
import com.creative.share.apps.wash_squad_driver.interfaces.Listeners;
import com.creative.share.apps.wash_squad_driver.language.LanguageHelper;
import com.creative.share.apps.wash_squad_driver.models.ItemToUpload;
import com.creative.share.apps.wash_squad_driver.singleton.SingleTon;

import java.util.Locale;

import io.paperdb.Paper;

public class PaymentActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityPaymentBinding binding;
    private String lang;
    private ItemToUpload itemToUpload;
    private SingleTon singleTon;

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
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setLang(lang);
        binding.setBackListener(this);

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


    @Override
    public void back() {
        finish();
    }

}
