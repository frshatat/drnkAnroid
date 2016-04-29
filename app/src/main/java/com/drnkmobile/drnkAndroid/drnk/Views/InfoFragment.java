package com.drnkmobile.drnkAndroid.drnk.Views;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
        Typeface face=Typeface.createFromAsset(rootView.getContext().getAssets(),"fonts/AvenirLTStd-Light.ttf");
        phoneNumber.setTypeface(face);
        businessHours.setTypeface(face);
        businessAddress.setTypeface(face);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }
                catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });



        return rootView;
    }
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }
}



