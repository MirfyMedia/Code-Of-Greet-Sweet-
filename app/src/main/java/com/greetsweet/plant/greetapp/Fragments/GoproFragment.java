package com.greetsweet.plant.greetapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greetsweet.plant.R;


public class GoproFragment extends Fragment {

    public GoproFragment() {
        // Required empty public constructor
    }

    public static GoproFragment newInstance(String param1, String param2) {
        GoproFragment fragment = new GoproFragment();
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
        return inflater.inflate(R.layout.fragment_gopro, container, false);
    }

}
