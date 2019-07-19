package com.greetsweet.plant.greetapp.Fragments.More;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greetsweet.plant.R;


public class CustomOTPFragment extends Fragment {

    public CustomOTPFragment() {
        // Required empty public constructor
    }

    public static CustomOTPFragment newInstance(String param1, String param2) {
        CustomOTPFragment fragment = new CustomOTPFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_custom_otp, container, false);


        return  view;
    }

}
