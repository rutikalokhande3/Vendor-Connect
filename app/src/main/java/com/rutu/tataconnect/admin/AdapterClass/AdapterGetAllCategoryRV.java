package com.rutu.tataconnect.admin.AdapterClass;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rutu.tataconnect.POJOGetAllCategory;
import com.rutu.tataconnect.R;


import java.util.List;

public class AdapterGetAllCategoryRV  extends  RecyclerView.Adapter<AdapterGetAllCategoryRV.ViewHolder> {

    List<POJOGetAllCategory> pojoGetAllCategories;
    Activity activity;

    public AdapterGetAllCategoryRV(List<POJOGetAllCategory> pojoGetAllCategories, Activity activity) {
        this.pojoGetAllCategories = pojoGetAllCategories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(activity).inflate(R.layout.lv_get_all_category,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
         POJOGetAllCategory obj = pojoGetAllCategories.get(position);
         viewHolder.tvCategoryName.setText(obj.getCategoryName());

        Glide.with(activity).load("http://192.168.155.54:80/TataConnnectAPI/images/"+obj.getCategoryImage())
                .skipMemoryCache(false)
                .error(R.drawable.imagenotavl)
                .into(viewHolder.ivCategoryImage);
    }

    @Override
    public int getItemCount()  {
        return pojoGetAllCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCategoryImage;
        TextView tvCategoryName;
        CardView cvCardList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvCardList = itemView.findViewById(R.id.cvCategoryList);
            ivCategoryImage = itemView.findViewById(R.id.ivcategoryimage);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
    }


}
