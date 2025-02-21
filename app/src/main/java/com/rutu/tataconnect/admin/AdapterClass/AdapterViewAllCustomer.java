package com.rutu.tataconnect.admin.AdapterClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rutu.tataconnect.AdapterCategoryWiseProduct;
import com.rutu.tataconnect.Common.Urls;
import com.rutu.tataconnect.MyProfileActivity;
import com.rutu.tataconnect.POJOCategoryWiseProduct;
import com.rutu.tataconnect.R;
import com.rutu.tataconnect.admin.POJOClass.POJOViewAllCustomerDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

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
            holder.deleteUser = view.findViewById(R.id.btnAllCustomerDeleteUser);



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

        holder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(activity);
                ad.setTitle("Delete User");
                ad.setMessage("Are you Sure You Want to Delete User");
                ad.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                ad.setNegativeButton("Delete User", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       deleteUser(obj.getUsername(),position);
                    }

                    private void deleteUser(String username, int position)
                    {
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();

                        params.put("username",username);
                        client.post(Urls.deleteUserWebService,params,new JsonHttpResponseHandler(){

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    String status = response.getString("success");

                                    if (status.equals("1"))
                                    {
                                        pojoViewAllCustomerDetailsList.remove(position);
                                        notifyDataSetChanged();
                                    }

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Toast.makeText(activity, "Server Error ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        return view;
    }

    class ViewHolder
    {
        ImageView ivProfilePhoto;
        TextView tvName,tvMobileNo,tvEmail,tvAddress,tvUsername;

        AppCompatButton deleteUser;

    }
}
