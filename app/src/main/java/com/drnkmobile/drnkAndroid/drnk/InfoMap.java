package com.drnkmobile.drnkAndroid.drnk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.drnkmobile.drnkAndroid.app.R;

public class InfoMap extends Fragment {

    private String businessLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_info_map, container, false);

        businessLocation =getActivity().getIntent().getExtras().getString("businessAddress");

        return rootView;
    }

    }


