package com.rutu.tataconnect;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rutu.tataconnect.Common.NetworkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    SharedPreferences prefrences;
    SharedPreferences.Editor editor;


    ProgressDialog progressDialog;


    String[] item = {"Afganistan","Austrellia","Argentina","Austria","Bangladesh","Bhutan","Brazil","Cambodia","Canada","Central Africa Republic","China","Colombia","Gambia,The","Georgia","Germany","Greece","Hong Kong","Haiti","Holy See","Hungary","Iceland","Indonesia","Iran","Iraq","Israel","Italy","India","Japan","Jordan","Korea, North","Korea, South","Liberia","Malaysia","Mali","Marshall Islands","Mexico","Monaco","Mongolia","Morocco","Nepal","Netherlands","New Zealand","Niger","North Korea","Oman ","Pakistan","Russia ","Saudi Arabia","Serbia","Singapore","Sri Lanka","Sweden","Switzerland","Taiwan","Tajikistan","Thailand","Tonga","Trinidad and Tobago ","Turkey","Ukraine","United Arab Emirates","United Kingdom","Venezuela","Yemen","Zambia","Zimbabwe","Punjab"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;

    EditText country ,name , mbno , email , username , password ;

    Button btnRgs;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();


    //@SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        prefrences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
        editor = prefrences.edit();
/*
        autoCompleteTextView = findViewById(R.id.RgsCountry);
        adapterItem =new ArrayAdapter<String>(this,R.layout.list_item, item);



        autoCompleteTextView.setAdapter(adapterItem);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(RegistrationActivity.this,"item:" +  item,Toast.LENGTH_SHORT).show();

            }
        });
*/
        country = findViewById(R.id.RgsCountry);
        name = findViewById(R.id.RgsName);
        mbno = findViewById(R.id.RgsMbNo);
        email = findViewById(R.id.RgsEmail);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        btnRgs = findViewById(R.id.RgsBtn);


        btnRgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(country.getText().toString().isEmpty()){
                    country.setError("Please Enter you Country");
                } else if (name.getText().toString().isEmpty()) {
                    name.setError("Please Enter your Name ");
                } else if (mbno.getText().toString().isEmpty()) {
                    mbno.setError("Please Enter your Mobile Number");
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("Please Enter your Email");
                } else if (username.getText().toString().isEmpty()) {
                    username.setError("Please Enter your Username");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Please Enter your Password");
                }
                else
                {
                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Please Wait.");
                    progressDialog.setMessage("Registration is in Process");
                    progressDialog.setCanceledOnTouchOutside(true);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + mbno.getText().toString(),
                            60, TimeUnit.SECONDS, RegistrationActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this,"Verified Successfully!",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this,"Verification Failed!",Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent i = new Intent(RegistrationActivity.this,OTPVerifyActivity.class);
                                    i.putExtra("verificationCode",verificationCode);
                                    i.putExtra("country",country.getText().toString());
                                    i.putExtra("name",name.getText().toString());
                                    i.putExtra("mobileno",mbno.getText().toString());
                                    i.putExtra("emailid",email.getText().toString());
                                    i.putExtra("username",username.getText().toString());
                                    i.putExtra("password",mbno.getText().toString());
                                    startActivity(i);


                                }
                            }
                    );





                }
            }
        });

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

}