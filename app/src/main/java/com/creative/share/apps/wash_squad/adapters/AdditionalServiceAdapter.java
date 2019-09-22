package com.creative.share.apps.wash_squad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.creative.share.apps.wash_squad.R;
import com.creative.share.apps.wash_squad.activities_fragments.activity_service_details.ServiceDetailsActivity;
import com.creative.share.apps.wash_squad.databinding.AdditionalServiceRowBinding;
import com.creative.share.apps.wash_squad.models.ServiceDataModel;

import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class AdditionalServiceAdapter extends RecyclerView.Adapter<AdditionalServiceAdapter.MyHolder> {

    private List<ServiceDataModel.Level3> serviceModelList;
    private Context context;
    private String lang;
    private ServiceDetailsActivity activity;

    public AdditionalServiceAdapter(List<ServiceDataModel.Level3> serviceModelList, Context context) {
        this.serviceModelList = serviceModelList;
        this.context = context;
        activity = (ServiceDetailsActivity) context;
        Paper.init(context);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdditionalServiceRowBinding additionalServiceRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.additional_service_row, parent, false);
        return new MyHolder(additionalServiceRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ServiceDataModel.Level3 serviceModel = serviceModelList.get(position);
        holder.additionalServiceRowBinding.setLang(lang);
        holder.additionalServiceRowBinding.setLevel3(serviceModel);


        holder.additionalServiceRowBinding.checkbox.setOnClickListener(view -> {

            if (holder.additionalServiceRowBinding.checkbox.isChecked()) {
                ServiceDataModel.Level3 serviceModel1 = serviceModelList.get(holder.getAdapterPosition());
                activity.setItemAdditionService(serviceModel1);


            } else {
                ServiceDataModel.Level3 serviceModel1 = serviceModelList.get(holder.getAdapterPosition());

                activity.removeAdditionalItem(serviceModel1);

            }



        });


    }

    @Override
    public int getItemCount() {
        return serviceModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private AdditionalServiceRowBinding additionalServiceRowBinding;

        public MyHolder(@NonNull AdditionalServiceRowBinding additionalServiceRowBinding) {
            super(additionalServiceRowBinding.getRoot());
            this.additionalServiceRowBinding = additionalServiceRowBinding;
        }
    }

}
