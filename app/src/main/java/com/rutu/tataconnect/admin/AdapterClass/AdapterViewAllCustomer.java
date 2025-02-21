package com.rutu.tataconnect.admin.AdapterClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rutu.tataconnect.AdapterCategoryWiseProduct;
import com.rutu.tataconnect.MyProfileActivity;
import com.rutu.tataconnect.POJOCategoryWiseProduct;
import com.rutu.tataconnect.R;
import com.rutu.tataconnect.admin.POJOClass.POJOViewAllCustomerDetails;

import java.util.List;

public class AdapterViewAllCustomer extends BaseAdapter {

    List<POJOViewAllCustomerDetails> pojoViewAllCustomerDetailsList;
    Activity activity;


    public AdapterViewAllCustomer(List<POJOViewAllCustomerDetails> pojoViewAllCustomerDetailsList, Activity activity) {
        this.pojoViewAllCustomerDetailsList = pojoViewAllCustomerDetailsList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojoViewAllCustomerDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoViewAllCustomerDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final AdapterViewAllCustomer.ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null)
        {
            holder = new AdapterViewAllCustomer.ViewHolder();
            view = inflater.inflate(R.layout.lv_view_all_customer_details,null);

            holder.ivProfilePhoto = view.findViewById(R.id.ivAllCustomerProfileImage);
            holder.tvName = view.findViewById(R.id.tvAllCustomerName);
            holder.tvMobileNo = view.findViewById(R.id.tvAllCustomerMobile);
            holder.tvEmail = view.findViewById(R.id.tvAllCustomerEmail);
            holder.tvAddress = view.findViewById(R.id.tvAllCustomerAddress);
            holder.tvUsername = view.findViewById(R.id.tvAllCustomerUsername);


            view.setTag(holder);

        }
        else
        {
            holder = (AdapterViewAllCustomer.ViewHolder) view.getTag();

        }
        final POJOViewAllCustomerDetails obj = pojoViewAllCustomerDetailsList.get(position);
        holder.tvName.setText(obj.getName());
        holder.tvMobileNo.setText(obj.getName());
        holder.tvEmail.setText(obj.getName());
        holder.tvAddress.setText(obj.getName());
        holder.tvUsername.setText(obj.getName());
        holder.tvName.setText(obj.getName());

        Glide.with(activity)
                .load("http://192.168.1.38:80/TataConnnectAPI/images/"+obj.getImages())
                .skipMemoryCache(true)
                .error(R.drawable.imagenotavl)
                .into(holder.ivProfilePhoto);

        return view;
    }

    class ViewHolder
    {
        ImageView ivProfilePhoto;
        TextView tvName,tvMobileNo,tvEmail,tvAddress,tvUsername;
    }
}
