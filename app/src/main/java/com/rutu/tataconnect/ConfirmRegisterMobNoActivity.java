package com.rutu.tataconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ConfirmRegisterMobNoActivity extends AppCompatActivity {

    AppCompatEditText registerMobNo;
    AppCompatButton btnNxt;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm_register_mob_no);

        registerMobNo = findViewById(R.id.ValidNumber);
        btnNxt = findViewById(R.id.btnNext);

        btnNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerMobNo.getText().toString().isEmpty())
                {
                    registerMobNo.setError("Please Enter Mobile No");
                } else if (registerMobNo.getText().toString().length() != 10) {
                    registerMobNo.setError("Please Enter Valid Mobile Number");
                }
                else
                {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + registerMobNo.getText().toString(),
                            60, TimeUnit.SECONDS, ConfirmRegisterMobNoActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ConfirmRegisterMobNoActivity.this,"Verified Successfully!",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ConfirmRegisterMobNoActivity.this,"Verification Failed!",Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent i = new Intent(ConfirmRegisterMobNoActivity.this, ForgetPasswordActivity.class);
                                    i.putExtra("verificationCode",verificationCode);
                                    i.putExtra("mobileno",registerMobNo.getText().toString());

                                    startActivity(i);


                                }
                            }
                    );
                }

            }
        });

    }
}