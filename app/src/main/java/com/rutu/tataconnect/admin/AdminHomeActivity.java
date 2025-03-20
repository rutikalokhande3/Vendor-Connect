package com.rutu.tataconnect.admin;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rutu.tataconnect.Common.Urls;
import com.rutu.tataconnect.HomeActivity;
import com.rutu.tataconnect.POJOGetAllCategory;
import com.rutu.tataconnect.QRCodeActivity;
import com.rutu.tataconnect.R;
import com.rutu.tataconnect.ViewAllCustomerActivity;
import com.rutu.tataconnect.ViewAllCustomerLocationInMapActivity;
import com.rutu.tataconnect.admin.AdapterClass.AdapterGetAllCategoryRV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {


    RecyclerView rvGetAllCategory;
    List<POJOGetAllCategory> pojoGetAllCategories;
    AdapterGetAllCategoryRV adapterGetAllCategoryRV;

    CardView cvAllCustomerLocationInMap,cvAllCustomerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);
        Toast.makeText(this,"Admin Home Activity", Toast.LENGTH_SHORT).show();

        rvGetAllCategory = findViewById(R.id.rvCategoryWiseProductListofProduct);
        rvGetAllCategory.setLayoutManager(new GridLayoutManager(AdminHomeActivity.this,2,
                GridLayoutManager.HORIZONTAL,false));

        cvAllCustomerLocationInMap = findViewById(R.id.cvAdminHomeCustomerLocation);
        cvAllCustomerDetails = findViewById(R.id.cvAdminHomeCustomerDetails);





        cvAllCustomerLocationInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ViewAllCustomerLocationInMapActivity.class);
                startActivity(intent);
            }
        });

        cvAllCustomerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ViewAllCustomerActivity.class);
                startActivity(intent);
            }
        });


        pojoGetAllCategories = new ArrayList<>();
        adapterGetAllCategoryRV = new AdapterGetAllCategoryRV(pojoGetAllCategories,this);
        rvGetAllCategory.setAdapter(adapterGetAllCategoryRV);

        getAllCategory();
    }

    private void getAllCategory() {
        RequestQueue requestQueue = Volley.newRequestQueue(AdminHomeActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Urls.getAllCategoryDetailsWebService,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("getAllCategory");

                            for (int i = 0 ; i< jsonArray.length();i++)
                            {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String strID = jsonObject1.getString("id");
                                String strCategoryImage = jsonObject1.getString("categoryimage");
                                String strCategoryName = jsonObject1.getString("categoryname");

                                pojoGetAllCategories.add(new POJOGetAllCategory(strID,strCategoryImage,strCategoryName));



                            }

                            adapterGetAllCategoryRV.notifyDataSetChanged();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AdminHomeActivity.this,"Server Error",Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homeMenuAdminScanQRCode) {
            Intent intent = new Intent(AdminHomeActivity.this, ScanQRCodeActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
