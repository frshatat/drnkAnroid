package com.drnkmobile.drnkAndroid.drnk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.drnkmobile.drnkAndroid.app.R;

public class InfoFragment extends Fragment {
    private TextView phoneNumber;
    private TextView businessHours;
    private TextView businessAddress;
    private String businessPhoneNumber;
    private String businessCurrentHours;
    private String businessLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        businessPhoneNumber = getActivity().getIntent().getExtras().getString("businessPhoneNumber");
        businessCurrentHours = getActivity().getIntent().getExtras().getString("businessHours");
        businessLocation = getActivity().getIntent().getExtras().getString("businessAddress");
        phoneNumber = (TextView) rootView.findViewById(R.id.phoneNumberField);
        businessHours = (TextView) rootView.findViewById(R.id.businesshoursField);
        businessAddress = (TextView) rootView.findViewById(R.id.locationField);
        phoneNumber.setText(businessPhoneNumber);
        businessHours.setText(businessCurrentHours);
        businessAddress.setText(businessLocation);
        return rootView;
    }
}
