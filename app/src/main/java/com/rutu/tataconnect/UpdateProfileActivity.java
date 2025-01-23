package com.rutu.tataconnect;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rutu.tataconnect.Common.Urls;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText etName,etMobileNo,etEmailId,etUsername;
    AppCompatButton btnUpdateProfile;

    String strName,strMobileNo,strEmailId,strUsername;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);

        etName = findViewById(R.id.updName);
        etMobileNo = findViewById(R.id.updMobileNo);
        etEmailId = findViewById(R.id.updEmail);
        etUsername = findViewById(R.id.updUsername);
        btnUpdateProfile = findViewById(R.id.btnUpdate);

        strName = getIntent().getStringExtra("name");
        strMobileNo = getIntent().getStringExtra("mobileno");
        strEmailId = getIntent().getStringExtra("emailid");
        strUsername = getIntent().getStringExtra("username");

        etName.setText(strName);
        etMobileNo.setText(strMobileNo);
        etEmailId.setText(strEmailId);
        etUsername.setText(strUsername);

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(UpdateProfileActivity.this);
                progressDialog.setTitle("Updating Profile");
                progressDialog.setMessage("Please Wait..");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                updateProfile();
            }


        });


    }

    private void updateProfile() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name",etName.getText().toString());
        params.put("mobileno",etMobileNo.getText().toString());
        params.put("emailid",etMobileNo.getText().toString());
        params.put("username",etUsername.getText().toString());

        client.post(Urls.updateProfileWebService,params,new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });






    }
}