package com.rutu.tataconnect;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterGetAllCategoryDetails extends BaseAdapter {

    //baseadapter = multiple view load show
    //AdapterGetAllCategoryDetails show w=multiple collect show listview

    List<POJOGetAllCategory> pojoGetAllCategories;
    Activity activity;


    public AdapterGetAllCategoryDetails(List<POJOGetAllCategory> pojoGetAllCategories, Activity activity) {
        this.pojoGetAllCategories = pojoGetAllCategories;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoGetAllCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoGetAllCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if(view==null)
        {

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.lv_get_all_category,null);
            holder.ivCategoryImage = view.findViewById(R.id.ivcategoryimage);
            holder.tvCategoryName = view.findViewById(R.id.tvCategoryName);
            holder.cvCardList = view.findViewById(R.id.cvCategoryList);

            view.setTag(holder);
        }
        else
        {

            holder = (ViewHolder) view.getTag();

        }

        final POJOGetAllCategory obj = pojoGetAllCategories.get(position);
        holder.tvCategoryName.setText(obj.getCategoryName());

        Glide.with(activity).load("http://192.168.1.38:80/TataConnnectAPI/images/"+obj.getCategoryImage())
                .skipMemoryCache(true)
                .error(R.drawable.grocery)
                .into(holder.ivCategoryImage);

        holder.cvCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CategoryWiseProductActivity.class);
                i.putExtra("categoryname",obj.getCategoryName());
                activity.startActivity(i);
            }
        });


        return view;
    }

    class ViewHolder
    {
        ImageView ivCategoryImage;
        TextView tvCategoryName;
        CardView cvCardList;


    }


}
