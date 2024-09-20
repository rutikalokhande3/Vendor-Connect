package com.rutu.tataconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    TextView rgsNumber, resendOtp;
    EditText ioOne, ioTwo, ioThree, ioFour, ioFive, ioSix;
    AppCompatButton btnvrf;

    ProgressDialog progressDialog;

    private String strVerification, strMobileno;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverify);

        rgsNumber = findViewById(R.id.registerNumber);
        resendOtp = findViewById(R.id.resendOtp);
        ioOne = findViewById(R.id.etInputCode1);
        ioTwo = findViewById(R.id.etInputCode2);
        ioThree = findViewById(R.id.etInputCode3);
        ioFour = findViewById(R.id.etInputCode4);
        ioFive = findViewById(R.id.etInputCode5);
        ioSix = findViewById(R.id.etInputCode6);
        btnvrf = findViewById(R.id.verifyBtn);

        strVerification  = getIntent().getStringExtra("verificationCode");
        strMobileno  = getIntent().getStringExtra("mobileno");

        rgsNumber.setText(strMobileno);



        btnvrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ioOne.getText().toString().trim().isEmpty() || ioTwo.getText().toString().trim().isEmpty()
                        || ioThree.getText().toString().trim().isEmpty() || ioFour.getText().toString().trim().isEmpty()
                        || ioFive.getText().toString().trim().isEmpty() || ioSix.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(ForgetPasswordActivity.this,"Please Enter Valid OTP",Toast.LENGTH_SHORT).show();
                }

                String otpCode = ioOne.getText().toString()+ioTwo.getText().toString()
                        +ioThree.getText().toString()+ioFour.getText().toString()
                        +ioFive.getText().toString()+ioSix.getText().toString();

                if (strVerification!=null)
                {
                    progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
                    progressDialog.setTitle("Verifying OTP");
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            strVerification,otpCode);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {

                                        progressDialog.dismiss();
                                        Intent i = new Intent(ForgetPasswordActivity.this,NewPasswordActivity.class);
                                        i.putExtra("mobile",strMobileno);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(ForgetPasswordActivity.this,"OTP Verification Failed",Toast.LENGTH_SHORT).show();
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
                        60, TimeUnit.SECONDS, ForgetPasswordActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressDialog.dismiss();
                                Toast.makeText(ForgetPasswordActivity.this,"Verified Successfully!",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressDialog.dismiss();
                                Toast.makeText(ForgetPasswordActivity.this,"Verification Failed!",Toast.LENGTH_SHORT).show();

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

