package com.rutu.tataconnect.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.encoder.QRCode;
import com.rutu.tataconnect.R;

public class ScanQRCodeActivity extends AppCompatActivity {

    TextView tvQRCodeResultText;
    AppCompatButton btnScanQRCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        tvQRCodeResultText = findViewById(R.id.tvScanQRCodeText);
        btnScanQRCode = findViewById(R.id.acbtnQRCodeScanQR);

        btnScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 IntentIntegrator integrator = new IntentIntegrator(ScanQRCodeActivity.this);
                 integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                 integrator.setPrompt("Scan");
                 integrator.setCameraId(0);
                 integrator.setBeepEnabled(true);
                 integrator.setBarcodeImageEnabled(true);
                 integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents() == null)
            {
                Toast.makeText(this,"Scanning Has Been Cancled",Toast.LENGTH_SHORT);
            }
            else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT);
                tvQRCodeResultText.setText(result.getContents());
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
