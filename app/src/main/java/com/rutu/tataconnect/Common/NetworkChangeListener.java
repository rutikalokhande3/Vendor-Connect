package com.rutu.tataconnect.Common;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.rutu.tataconnect.R;

public class NetworkChangeListener extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(!NetworkDetails.isConnectedToInternet(context))
        {
            AlertDialog.Builder ad = new AlertDialog.Builder(context); //dialog build.
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.internet_connection_dialog,null);
            ad.setView(layout_dialog);

            AppCompatButton btnRetry = layout_dialog.findViewById(R.id.btnRetry);

            AlertDialog alertDialog = ad.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);

            btnRetry.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context,intent);
                }
            });

        }
        else
        {
            Toast.makeText(context, "Your Internet is Connected", Toast.LENGTH_SHORT).show();
        }
    }
}

//Activity =>
//Services => Long Running Operation background
//BroadcastReceiver => System Communication and app Communicate.
//Content Provider => Data Store, Data Pass














