package com.rutu.tataconnect;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CategoryWiseProductActivity extends AppCompatActivity {

    SearchView searchCategoryWiseProduct;
    ListView lvCategoryWiseProduct;
    TextView tvNoProductAvailable;

    String strCategoryName;

    List<POJOCategoryWiseProduct> pojoCategoryWiseProductList;
    AdapterCategoryWiseProduct adapterCategoryWiseProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_wise_product);
        searchCategoryWiseProduct = findViewById(R.id.svCategoryWiseProductSearchProduct);
        lvCategoryWiseProduct = findViewById(R.id.lvCategoryWiseProductListofProduct);
        tvNoProductAvailable = findViewById(R.id.tvCategoryWiseProductNoProductAvailable);

        pojoCategoryWiseProductList = new ArrayList<>();

        strCategoryName = getIntent().getStringExtra("categoryname");

        getCategoryWiseProductList();

        searchCategoryWiseProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchProductbyCategory(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchProductbyCategory(s);
                return false;
            }
        });

    }

    private void searchProductbyCategory(String s) {
        List<POJOCategoryWiseProduct> templist = new ArrayList<>();
        templist.clear();

        for(POJOCategoryWiseProduct obj:pojoCategoryWiseProductList) {
            if (obj.getCategoryname().toUpperCase().contains(s.toUpperCase()) ||
                    obj.getProductname().toUpperCase().contains(s.toUpperCase()) ||
                    obj.getShopname().toUpperCase().contains(s.toUpperCase()) ||
                    obj.getProductprice().toUpperCase().contains(s.toUpperCase()) ||
                    obj.getCategoryname().toUpperCase().contains((s.toUpperCase()))) {
                templist.add(obj);
            }
        }
            adapterCategoryWiseProduct = new AdapterCategoryWiseProduct(templist, this);
            lvCategoryWiseProduct.setAdapter(adapterCategoryWiseProduct);


    }

    private void getCategoryWiseProductList()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("categoryname",strCategoryName);

        client.post("http://192.168.15.54:80/TataConnnectAPI/categoryWiseProduct.php",params,new
                JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("getCategoryWiseProduct");
                            if (jsonArray.isNull(0))
                            {
                                lvCategoryWiseProduct.setVisibility(View.GONE);
                                tvNoProductAvailable.setVisibility(View.VISIBLE);
                            }
                            for( int i = 0 ; i<jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strid = jsonObject.getString("id");
                                String strcategoryname = jsonObject.getString("categoryname");
                                String strshopname = jsonObject.getString("shopname");
                                String strproductimage = jsonObject.getString("productimage");
                                String strproductname = jsonObject.getString("productname");
                                String strproductprice = jsonObject.getString("productprice");
                                String strproductrating = jsonObject.getString("productrating");
                                String strproductoffer = jsonObject.getString("productoffer");

                                pojoCategoryWiseProductList.add(new POJOCategoryWiseProduct(strid,strcategoryname,
                                                             strshopname,strproductimage,strproductname,
                                                              strproductprice,strproductrating,strproductoffer));

                            }

                            adapterCategoryWiseProduct = new AdapterCategoryWiseProduct(pojoCategoryWiseProductList,
                                                         CategoryWiseProductActivity.this);

                            lvCategoryWiseProduct.setAdapter(adapterCategoryWiseProduct);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(CategoryWiseProductActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                    }
                }



        );

    }

}