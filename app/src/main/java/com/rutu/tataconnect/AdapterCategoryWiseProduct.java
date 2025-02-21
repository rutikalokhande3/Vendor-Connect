package com.rutu.tataconnect;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterCategoryWiseProduct extends BaseAdapter
{

    List<POJOCategoryWiseProduct> list;
    Activity activity;

    public AdapterCategoryWiseProduct(List<POJOCategoryWiseProduct> pojoCategoryWiseProductList, Activity activity) {
        this.list = pojoCategoryWiseProductList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.lv_categorywiseproduct,null);

            holder.ivCategoryWiseProductImage = view.findViewById(R.id.ivCategoryWiseProductImage);
            holder.tvShopName = view.findViewById(R.id.tvCategoryWiseProductShopName);
            holder.tvShopRating = view.findViewById(R.id.tvCategoryWiseProductShopRating);
            holder.tvProductName = view.findViewById(R.id.tvCategoryWiseProductName);
            holder.tvProductCategory = view.findViewById(R.id.tvCategoryWiseProductCategory);
            holder.tvProductPrice = view.findViewById(R.id.tvCategoryWiseProductPrice);
            holder.tvProductOffer = view.findViewById(R.id.tvCategoryWiseProductOffer);

            view.setTag(holder);

        }
        else
        {
           holder = (ViewHolder) view.getTag();

        }
         final POJOCategoryWiseProduct obj = list.get(position);
        holder.tvShopName.setText(obj.getShopname());
        holder.tvShopRating.setText(obj.getProductrating());
        holder.tvProductName.setText(obj.getProductname());
        holder.tvProductCategory.setText(obj.getCategoryname());
        holder.tvProductPrice.setText(obj.getProductprice());
        holder.tvProductOffer.setText(obj.getProductoffer());

        Glide.with(activity)
                .load("http://192.168.1.38:80/TataConnnectAPI/images/"+obj.getProductimage())
                .skipMemoryCache(true)
                .error(R.drawable.notavbl)
                .into(holder.ivCategoryWiseProductImage);





        return view;
    }

    class ViewHolder
    {
        ImageView ivCategoryWiseProductImage;
        TextView tvShopName,tvShopRating,tvProductName,
                tvProductCategory,tvProductPrice,tvProductOffer;


    }
}
