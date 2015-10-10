package com.drnkmobile.drnkAndroid.drnk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.drnkmobile.drnkAndroid.app.R;

public class TodayActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_today, container, false);

        return rootView;
    }
}
