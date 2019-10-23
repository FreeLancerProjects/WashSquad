package com.creative.share.apps.wash_squad.activities_fragments.activity_intro;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;



import com.creative.share.apps.wash_squad.R;
import com.creative.share.apps.wash_squad.activities_fragments.activity_sign_in.SignInActivity;
import com.creative.share.apps.wash_squad.databinding.ActivityIntroBinding;
import com.creative.share.apps.wash_squad.language.LanguageHelper;
import com.creative.share.apps.wash_squad.preferences.Preferences;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Locale;

import io.paperdb.Paper;

public class Intro_Activity extends AppCompatActivity {
ActivityIntroBinding binding;
    private ViewPager viewPager;
    private TabLayout indicator;
    private Button start;
    private Intro_Pager_Adapter intro_pager_adapter;
    Preferences preferences;
private  int count=0;
private ArrayList<View> views;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", Locale.getDefault().getLanguage())));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro_);
        if(savedInstanceState==null) {
            preferences=Preferences.newInstance();
            intro_pager_adapter = new Intro_Pager_Adapter(this);

            binding.tab1.setupWithViewPager(viewPager);
            binding.viewPager.setAdapter(intro_pager_adapter);
            views=new ArrayList<>();
binding.btnNext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        preferences.create_first_time(Intro_Activity.this,false);
        Intent i = new Intent(Intro_Activity.this, SignInActivity.class);
        startActivity(i);
        finish();
    }
});
            ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {


                @Override
                public void onPageSelected(int position) {
                    if (position == 4) {
                        viewPager.removeAllViews();
                    }
                    viewPager.setCurrentItem(position);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            };
        }
    }
    public class Intro_Pager_Adapter extends PagerAdapter {
        private int[] layouts = new int[]{R.layout.introslider1, R.layout.introslider2, R.layout.introslider3};
        private int image[]=new int[]{R.drawable.slider1,R.drawable.slider2,R.drawable.slider3};
        private LayoutInflater layoutInflater;
        Intro_Activity intro_activity;
        View view = null;
        public Intro_Pager_Adapter(Intro_Activity intro_activity) {
            this.intro_activity=intro_activity;
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
try {

    view = layoutInflater.inflate(layouts[position], container, false);
    ImageView imageView = view.findViewById(R.id.item_image);
Picasso.with(intro_activity).load(image[position]).fit().memoryPolicy(MemoryPolicy.NO_CACHE).into(imageView);
container.addView(view);





}catch (OutOfMemoryError e){
    Log.e("Error",e.getCause()+"");
}

            return view;
        }

        @Override
        public int getCount() {
            return (layouts != null) ? layouts.length : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return (view == obj);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup collection, int position, Object view) {
            /*View itemView = (View)view;
            collection.removeView(itemView);
            itemView=null;*/
        }
    }

}
