package com.creative.share.apps.wash_squad_driver.models;

import android.content.Context;
import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.creative.share.apps.wash_squad_driver.BR;
import com.creative.share.apps.wash_squad_driver.R;

import java.io.Serializable;

public class EditProfileModel extends BaseObservable implements Serializable {

    private String name;
    private String phone_code;
    private String phone;
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_phone_code = new ObservableField<>();
    public ObservableField<String> error_phone = new ObservableField<>();


    public EditProfileModel() {
        this.name = "";
        this.phone_code = "";
        this.phone="";
    }

    public EditProfileModel(String name, String phone_code, String phone) {
        setName(name);
        notifyPropertyChanged(BR.name);
        setPhone_code(phone_code);
        notifyPropertyChanged(BR.phone_code);
        setPhone(phone);
        notifyPropertyChanged(BR.phone);
        //setPassword(password);
        notifyPropertyChanged(BR.password);

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);


    }

    @Bindable
    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
        notifyPropertyChanged(BR.phone_code);

    }
    @Bindable

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);

    }





    public boolean isDataValid(Context context)
    {
        if (!TextUtils.isEmpty(phone_code)&&
                !TextUtils.isEmpty(phone)&&
                !TextUtils.isEmpty(name)

        )
        {
            error_name.set(null);
            error_phone_code.set(null);
            error_phone.set(null);

            return true;
        }else
        {
            if (name.isEmpty())
            {
                error_name.set(context.getString(R.string.field_req));
            }else
            {
                error_name.set(null);
            }


            if (phone_code.isEmpty())
            {
                error_phone_code.set(context.getString(R.string.field_req));
            }else
            {
                error_phone_code.set(null);
            }

            if (phone.isEmpty())
            {
                error_phone.set(context.getString(R.string.field_req));
            }else
            {
                error_phone.set(null);
            }







            return false;
        }
    }
}
