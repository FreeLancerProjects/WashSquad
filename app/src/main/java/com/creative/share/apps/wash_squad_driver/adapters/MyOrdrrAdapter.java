package com.creative.share.apps.wash_squad_driver.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.creative.share.apps.wash_squad_driver.R;
import com.creative.share.apps.wash_squad_driver.activities_fragments.activity_home.activity.HomeActivity;

import com.creative.share.apps.wash_squad_driver.databinding.LoadMoreBinding;
import com.creative.share.apps.wash_squad_driver.databinding.OrderRowBinding;
import com.creative.share.apps.wash_squad_driver.models.Order_Data_Model;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class MyOrdrrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_DATA = 1;
    private final int LOAD = 2;
    private List<Order_Data_Model.OrderModel> orderlist;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private HomeActivity activity;
    private Fragment fragment;
    int index = -1;

    public MyOrdrrAdapter(List<Order_Data_Model.OrderModel> orderlist, Context context, Fragment fragment) {
        this.orderlist = orderlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        this.activity = (HomeActivity) context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==ITEM_DATA) {
            OrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_row, parent, false);
            return new EventHolder(binding);
        }
        else {
            LoadMoreBinding binding = DataBindingUtil.inflate(inflater, R.layout.load_more,parent,false);
            return new LoadHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order_Data_Model.OrderModel order_orderModel = orderlist.get(position);
        if (holder instanceof EventHolder)
        {
        EventHolder eventHolder = (EventHolder) holder;
        eventHolder.binding.setNum((position + 1) + "");


        if (position == 0) {
            eventHolder.binding.view.setVisibility(View.GONE);
        } else {
            eventHolder.binding.view.setVisibility(View.VISIBLE);

        }
        eventHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
                if (orderlist.get(eventHolder.getLayoutPosition()).getRating() > 0&&orderlist.get(eventHolder.getLayoutPosition()).getStatus()!=3) {
                    activity.DisplayFragmentOrderDetials(orderlist.get(eventHolder.getLayoutPosition()));
                } else {
                    activity.DisplayFragmentOrderDetialsEvaluation(orderlist.get(eventHolder.getLayoutPosition()));
                }
            }
        });


        if (position < index) {
            ViewGroup.LayoutParams params1 = eventHolder.binding.tvNum.getLayoutParams();
            params1.height = 90;
            params1.width = 90;
            eventHolder.binding.view.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            eventHolder.binding.tvNum.setTextColor(activity.getResources().getColor(R.color.white));
            eventHolder.binding.tvNum.setBackground(activity.getResources().getDrawable(R.drawable.image_user_bg_color));
            //eventHolder.binding.tvNum.setLayoutParams(params1);
            if (position == 0) {
                eventHolder.binding.view.setVisibility(View.GONE);
            } else {
                eventHolder.binding.view.setVisibility(View.VISIBLE);

            }
        } else if (position == index) {
            ViewGroup.LayoutParams params = eventHolder.binding.tvNum.getLayoutParams();
            params.height = 105;
            params.width = 105;
            eventHolder.binding.view.setBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            eventHolder.binding.tvNum.setTextColor(activity.getResources().getColor(R.color.white));
            eventHolder.binding.tvNum.setBackground(activity.getResources().getDrawable(R.drawable.image_user_bg_color));
           // eventHolder.binding.tvNum.setLayoutParams(params);
            if (position == 0) {
                eventHolder.binding.view.setVisibility(View.GONE);
            } else {
                eventHolder.binding.view.setVisibility(View.VISIBLE);

            }

        } else if (position > index) {
            ViewGroup.LayoutParams params1 = eventHolder.binding.tvNum.getLayoutParams();
            params1.height = 90;
            params1.width = 90;
            eventHolder.binding.view.setBackgroundColor(activity.getResources().getColor(R.color.gray3));
            eventHolder.binding.tvNum.setTextColor(activity.getResources().getColor(R.color.black));
            eventHolder.binding.tvNum.setBackground(activity.getResources().getDrawable(R.drawable.image_user_bg));
          //  eventHolder.binding.tvNum.setLayoutParams(params1);
            if (position == 0) {
                eventHolder.binding.view.setVisibility(View.GONE);
            } else {
                eventHolder.binding.view.setVisibility(View.VISIBLE);

            }
        }
    }else
    {
        LoadHolder loadHolder = (LoadHolder) holder;
        loadHolder.binding.progBar.setIndeterminate(true);
    }
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public OrderRowBinding binding;

        public EventHolder(@NonNull OrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public class LoadHolder extends RecyclerView.ViewHolder {
        private LoadMoreBinding binding;
        public LoadHolder(@NonNull LoadMoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Order_Data_Model.OrderModel order_Model = orderlist.get(position);
        if (order_Model!=null)
        {
            return ITEM_DATA;
        }else
        {
            return LOAD;
        }

    }


}
