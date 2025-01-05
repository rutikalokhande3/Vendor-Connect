package com.rutu.tataconnect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CategoryFragment extends Fragment {

    ListView lvMultipleCategory;
    TextView tvNoCategory;

    List<POJOGetAllCategory> pojoGetAllCategories;
    AdapterGetAllCategoryDetails adapterGetAllCategoryDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        pojoGetAllCategories = new ArrayList<>();
        lvMultipleCategory = view.findViewById(R.id.lvCategoryFragmentMultipltCategory);
        tvNoCategory = view.findViewById(R.id.tvNoCategory);

        getAllCategory();

        return view;
    }

    private void getAllCategory() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        asyncHttpClient.post("http://192.168.98.54:80/TataConnnectAPI/getAllCategoryDetails.php",
                                  params,
                                  new JsonHttpResponseHandler(){
                                      @Override
                                      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                          super.onSuccess(statusCode, headers, response);

                                          try {
                                              JSONArray jsonArray = response.getJSONArray("getAllCategory");
                                              if (jsonArray.isNull(0))
                                              {
                                                  tvNoCategory.setVisibility(View.VISIBLE);
                                              }

                                              for (int i=0;i<jsonArray.length();i++)
                                              {
                                                  JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                  String strId = jsonObject.getString("id");
                                                  String strCategoryImage = jsonObject.getString("categoryimage");
                                                  String strCategoryName = jsonObject.getString("categoryname");
                                                  pojoGetAllCategories.add(new POJOGetAllCategory(strId,strCategoryImage,strCategoryName));
                                              }

                                              adapterGetAllCategoryDetails = new AdapterGetAllCategoryDetails(pojoGetAllCategories,
                                                                            getActivity());
                                              lvMultipleCategory.setAdapter(adapterGetAllCategoryDetails);

                                          } catch (JSONException e) {
                                              throw new RuntimeException(e);
                                          }

                                      }

                                      @Override
                                      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                          super.onFailure(statusCode, headers, throwable, errorResponse);
                                          Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_SHORT).show();
                                      }
                                  });

    }
}