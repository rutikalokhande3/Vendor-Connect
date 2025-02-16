
package com.rutu.tataconnect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    TextView marqueeText;



    private Object AnimationTypes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_home, container, false);
         marqueeText = view.findViewById(R.id.marqueeText);
        marqueeText.setSelected(true);





        return view;
    }
}