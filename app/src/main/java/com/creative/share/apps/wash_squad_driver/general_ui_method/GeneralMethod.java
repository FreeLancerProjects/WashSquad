package com.creative.share.apps.wash_squad_driver.general_ui_method;

import android.net.Uri;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.creative.share.apps.wash_squad_driver.tags.Tags;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter("image")
    public static void displayImageHome(ImageView imageView, int image_resource)
    {
        imageView.setImageResource(image_resource);
    }


    @BindingAdapter("serviceImage")
    public static void serviceImage(ImageView imageView,String endPoint)
    {
        Picasso.with(imageView.getContext()).load(Uri.parse(Tags.IMAGE_URL+endPoint)).fit().into(imageView);
    }




    @BindingAdapter({"date","time","time_type"})
    public static void displayDate (TextView textView,long date,String time,int time_type)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd/mmm",Locale.ENGLISH);
        String m_date = dateFormat.format(new Date(date));
        textView.setText(String.format("%s %s %s",m_date,time,time_type));

    }

    @BindingAdapter("rate")
    public static void rate (SimpleRatingBar simpleRatingBar, float rate)
    {
        SimpleRatingBar.AnimationBuilder builder = simpleRatingBar.getAnimationBuilder()
                .setRatingTarget(rate)
                .setDuration(1000)
                .setRepeatCount(0)
                .setInterpolator(new LinearInterpolator());
        builder.start();
    }







}
