
package com.rutu.tataconnect;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    TextView marqueeText;

    CardView cdmxplyr;



    private Object AnimationTypes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_home, container, false);
         marqueeText = view.findViewById(R.id.marqueeText);
        marqueeText.setSelected(true);

/*
        cdmxplyr = view.findViewById(R.id.cdMXPlayer);

        cdmxplyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MusicFragment.class);
                startActivity(i);
            }
        });

*/





        return view;
    }
}