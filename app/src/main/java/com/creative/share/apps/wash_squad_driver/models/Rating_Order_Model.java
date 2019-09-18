package com.creative.share.apps.wash_squad_driver.models;

import android.content.Context;
import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.creative.share.apps.wash_squad_driver.BR;
import com.creative.share.apps.wash_squad_driver.R;

import java.io.Serializable;

public class Rating_Order_Model extends BaseObservable implements Serializable {

    private String desc;
    private float rate=0.0f;
    public ObservableField<String> error_desc = new ObservableField<>();


    public Rating_Order_Model() {
        this.desc = "";

    }

    public Rating_Order_Model(String desc,float rate) {
        setDesc(desc);
        notifyPropertyChanged(BR.desc);
        setRate(rate);
        notifyPropertyChanged(BR.rate);

    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);


    }
    @Bindable
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isDataValid(Context context)
    {
        if (!TextUtils.isEmpty(desc)
        )
        {
            error_desc.set(null);


            return true;
        }else
        {
            if (desc.isEmpty())
            {
                error_desc.set(context.getString(R.string.field_req));
            }else
            {
                error_desc.set(null);
            }










            return false;
        }
    }
}
