package com.rutu.tataconnect;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rutu.tataconnect.Common.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class OTPVerifyActivity extends AppCompatActivity {

    TextView rgsNumber, resendOtp;
    EditText ioOne, ioTwo, ioThree, ioFour, ioFive, ioSix;
    AppCompatButton btnvrf;

    ProgressDialog progressDialog;

    private String strVerification, strCountry, strName, strMobileno, strEmail, strUsername, strPassword;

    double latitude, longitude;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverify);

        if (ActivityCompat.checkSelfPermission(OTPVerifyActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(OTPVerifyActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OTPVerifyActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION

                    }, 199);

        }
/*
        else {
            getUserCurrentLocation();
        }
*/
        rgsNumber = findViewById(R.id.registerNumber);
        resendOtp = findViewById(R.id.resendOtp);
        ioOne = findViewById(R.id.etInputCode1);
        ioTwo = findViewById(R.id.etInputCode2);
        ioThree = findViewById(R.id.etInputCode3);
        ioFour = findViewById(R.id.etInputCode4);
        ioFive = findViewById(R.id.etInputCode5);
        ioSix = findViewById(R.id.etInputCode6);
        btnvrf = findViewById(R.id.verifyBtn);

        strVerification = getIntent().getStringExtra("verificationCode");
        strCountry = getIntent().getStringExtra("country");
        strName = getIntent().getStringExtra("name");
        strMobileno = getIntent().getStringExtra("mobileno");
        strEmail = getIntent().getStringExtra("emailid");
        strUsername = getIntent().getStringExtra("username");
        strPassword = getIntent().getStringExtra("password");

        rgsNumber.setText(strMobileno);

        btnvrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ioOne.getText().toString().trim().isEmpty() || ioTwo.getText().toString().trim().isEmpty()
                        || ioThree.getText().toString().trim().isEmpty() || ioFour.getText().toString().trim().isEmpty()
                        || ioFive.getText().toString().trim().isEmpty() || ioSix.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OTPVerifyActivity.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                }

                String otpCode = ioOne.getText().toString() + ioTwo.getText().toString()
                        + ioThree.getText().toString() + ioFour.getText().toString()
                        + ioFive.getText().toString() + ioSix.getText().toString();

                if (strVerification != null) {
                    progressDialog = new ProgressDialog(OTPVerifyActivity.this);
                    progressDialog.setTitle("Verifying OTP");
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            strVerification, otpCode);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {

                                        progressDialog.dismiss();
                                        getUserCurrentLocation();

                                    } else
                                    {
                                        Toast.makeText(OTPVerifyActivity.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }

            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + strMobileno.toString(),
                        60, TimeUnit.SECONDS, OTPVerifyActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressDialog.dismiss();
                                Toast.makeText(OTPVerifyActivity.this, "Verified Successfully!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressDialog.dismiss();
                                Toast.makeText(OTPVerifyActivity.this, "Verification Failed!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                            }
                        }
                );

            }
        });

        setupInputOTP();

    }

    private void getUserCurrentLocation() {

        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(OTPVerifyActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(OTPVerifyActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
                    address = addressList.get(0).getAddressLine(0);
                    userRegisterDetail(latitude,longitude,address);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OTPVerifyActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void userRegisterDetail(double latitude, double longitude, String address) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("country",strCountry);
        params.put("name",strName);
        params.put("mobileno",strMobileno);
        params.put("emailid",strEmail);
        params.put("username",strUsername);
        params.put("password",strPassword);
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        params.put("address",address);

        client.post(Urls.registerUserWebService,params,
                new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            String status = response.getString("success");
                            if(status.equals("1"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(OTPVerifyActivity.this,"Registration Successfully ",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(OTPVerifyActivity.this,LoginActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(OTPVerifyActivity.this,"Already Data Present",Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        Toast.makeText(OTPVerifyActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }

    private void setupInputOTP()
    {
        ioOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!s.toString().trim().isEmpty())
                {
                    ioTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        ioTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!s.toString().trim().isEmpty())
                {
                    ioThree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        ioThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!s.toString().trim().isEmpty())
                {
                    ioFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        ioFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!s.toString().trim().isEmpty())
                {
                    ioFive.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        ioFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (!s.toString().trim().isEmpty())
                {
                    ioSix.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

    }
}