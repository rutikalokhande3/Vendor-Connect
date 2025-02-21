
package com.rutu.tataconnect;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rutu.tataconnect.Common.Urls;
import com.rutu.tataconnect.admin.AdapterClass.AdapterGetAllCategoryRV;
import com.rutu.tataconnect.admin.AdminHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView marqueeText;
    List<POJOGetAllCategory> pojoGetAllCategories;
    AdapterGetAllCategoryRV adapterGetAllCategoryRV;

    CardView cdmxplyr,cvAllCustomerLocationInMap;



    private Object AnimationTypes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_home, container, false);
         marqueeText = view.findViewById(R.id.marqueeText);
        marqueeText.setSelected(true);

        cvAllCustomerLocationInMap = view.findViewById(R.id.cvAdminHomeCustomerLocation);


        cvAllCustomerLocationInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAllCustomerLocationInMapActivity.class);
                startActivity(intent);
            }
        });






        return view;
    }



    
}