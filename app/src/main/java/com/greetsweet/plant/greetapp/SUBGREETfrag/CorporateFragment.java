package com.greetsweet.plant.greetapp.SUBGREETfrag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greetsweet.plant.R;


public class CorporateFragment extends Fragment {

    public CorporateFragment() {
        // Required empty public constructor
    }

    public static CorporateFragment newInstance(String param1, String param2) {
        CorporateFragment fragment = new CorporateFragment();
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
        return inflater.inflate(R.layout.fragment_corporate, container, false);
    }

}
