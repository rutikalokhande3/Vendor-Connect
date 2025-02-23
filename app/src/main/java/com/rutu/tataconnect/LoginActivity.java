package com.rutu.tataconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rutu.tataconnect.Common.NetworkChangeListener;
import com.rutu.tataconnect.Common.Urls;
import com.rutu.tataconnect.admin.AdminHomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity
{
    
    ImageView ivlogo;

    EditText username,password;
    Button login,signUp;
    TextView forgetPass;
    
    ProgressDialog progressDialog;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    AppCompatButton btnSignGoogle;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();
      
        ivlogo = findViewById(R.id.loginLobo);
        signUp = findViewById(R.id.btnSignUp);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.btnLogin);
        btnSignGoogle = findViewById(R.id.SignGoogle);
        forgetPass = findViewById(R.id.ForgetPassword);

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ConfirmRegisterMobNoActivity.class);
                startActivity(i);
            }
        });


        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);

        btnSignGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }




        });
        
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty())
                {
                    username.setError("Please Enter your Username");
                }
                if(password.getText().toString().isEmpty())
                {
                    password.setError("Please Enter your Password");

                }
                else {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Please Wait..");
                    progressDialog.setMessage("Login Under Process..");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();


                    userLogin();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });


    }

    private void signIn()
    {
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i,999);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999)
        {
            Task<GoogleSignInAccount> task =GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent i = new Intent(LoginActivity.this,SignOutActivity.class);
                startActivity(i);
                finish();
            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this,"Something went wrong..",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,intentFilter);

    }



    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListener);
    }

    private void userLogin() {
        AsyncHttpClient client = new AsyncHttpClient();  //client - server communication
        RequestParams params = new RequestParams(); //data put

        params.put("username", username.getText().toString());
        params.put("password",password.getText().toString());

        client.post(Urls.loginUserWebService,params,new JsonHttpResponseHandler()
                {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        progressDialog.dismiss();

                      try
                      {
                          String status = response.getString("success");
                          String strUserrole = response.getString("userrole");

                          if(status.equals("1") && strUserrole.equals("user"))
                          {
                              progressDialog.dismiss();
                              Intent i =  new Intent(LoginActivity.this,HomeActivity.class);
                              editor.putString("username",username.getText().toString()).commit();
                              startActivity(i);
                              Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_SHORT)
                                      .show();
                             // finish();
                          } else if (status.equals("1") && strUserrole.equals("admin")) {
                              Intent i =  new Intent(LoginActivity.this, AdminHomeActivity.class);
                              startActivity(i);
                             // finish();

                          } else 
                          {
                              Toast.makeText(LoginActivity.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                          }
                      }
                      catch (JSONException e)
                      {
                          throw new RuntimeException(e);
                      }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}