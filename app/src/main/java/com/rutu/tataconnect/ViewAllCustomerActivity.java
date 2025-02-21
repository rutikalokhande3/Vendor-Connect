package com.rutu.tataconnect;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rutu.tataconnect.Common.Urls;
import com.rutu.tataconnect.admin.AdapterClass.AdapterGetAllCategoryRV;
import com.rutu.tataconnect.admin.AdapterClass.AdapterViewAllCustomer;
import com.rutu.tataconnect.admin.POJOClass.POJOViewAllCustomerDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewAllCustomerActivity extends AppCompatActivity {
    
    SearchView searchCustomer;
    ListView lvShowAllCustomer;
    TextView tvNoCustomerAvailable;
    
    ProgressDialog progressDialog;

    List<POJOViewAllCustomerDetails> pojoViewAllCustomerDetails;
    AdapterViewAllCustomer adapterViewAllCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_customer);

        pojoViewAllCustomerDetails = new ArrayList<>();
        
        searchCustomer = findViewById(R.id.svViewAllCustomerFragmentSearchCustomer);
        
        lvShowAllCustomer = findViewById(R.id.lvViewAllCustomerShowAllCustomer);
        
        tvNoCustomerAvailable = findViewById(R.id.tvViewAllCustomerNoCustomerAvailable);

        progressDialog = new ProgressDialog(ViewAllCustomerActivity.this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Customer Detail Under Process..");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        
        viewAllCustomer();



    }

    private void viewAllCustomer() {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        asyncHttpClient.post(Urls.getGetAllCustomerDetailsWebService,
                params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = response.getJSONArray("getAllCustomerDetails");
                            if (jsonArray.isNull(0)) {
                                tvNoCustomerAvailable.setVisibility(View.VISIBLE);
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strId = jsonObject.getString("id");
                                String strImage = jsonObject.getString("images");
                                String strName = jsonObject.getString("name");
                                String strMobileNo = jsonObject.getString("mobileno");
                                String strEmailId = jsonObject.getString("emailid");
                                Double dblLatitude = Double.parseDouble("latitude");
                                Double dblLongitude = Double.parseDouble("longitude");
                                String strAddress = jsonObject.getString("address");
                                String strUsername = jsonObject.getString("username");




                                pojoViewAllCustomerDetails.add(new
                                        POJOViewAllCustomerDetails(strId, strImage,strName,
                                        strMobileNo,strEmailId,dblLatitude,dblLongitude,strAddress,strUsername));
                            }

                            adapterViewAllCustomer = new AdapterViewAllCustomer(pojoViewAllCustomerDetails,
                                    ViewAllCustomerActivity.this);
                            lvShowAllCustomer.setAdapter(adapterViewAllCustomer);

                            //rvMultipleCategory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                            pojoViewAllCustomerDetails = new ArrayList<>();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(ViewAllCustomerActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}