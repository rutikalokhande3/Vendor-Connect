package com.rutu.tataconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rutu.tataconnect.Common.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NewPasswordActivity extends AppCompatActivity {

    String strMobileNum;
    AppCompatEditText newpass,confirmpass;
    AppCompatButton btnNext;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);

        strMobileNum = getIntent().getStringExtra("mobile");
        newpass = findViewById(R.id.newPassword);
        confirmpass = findViewById(R.id.confirmPassword);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpass.getText().toString().isEmpty() || confirmpass.getText().toString().isEmpty())
                {
                    Toast.makeText(NewPasswordActivity.this, "Please Enter New or Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!newpass.getText().toString().equals(confirmpass.getText().toString())) {
                    confirmpass.setError("Password did not match");
                }
                else
                {
                    progressDialog = new ProgressDialog(NewPasswordActivity.this);
                    progressDialog.setTitle("Updating Password");
                    progressDialog.setMessage("Please wait");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    forgetPassword();
                }
            }
        });

    }

    private void forgetPassword() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("mobile",strMobileNum);
        params.put("password",newpass.getText().toString());

        client.post(Urls.forgetPasswordWebService,params,
              new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String status = response.getString("Success");
                    if (status.equals("1"))
                    {
                        Toast.makeText(NewPasswordActivity.this,"Password changed!",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(NewPasswordActivity.this,LoginActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(NewPasswordActivity.this,"Password not Changed",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(NewPasswordActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
            }
        });

    }
}