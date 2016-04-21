package com.drnkmobile.drnkAndroid.drnk.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import com.drnkmobile.drnkAndroid.app.R;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.Business;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.BusinessFormatter;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.Parser;
import com.drnkmobile.drnkAndroid.drnk.Connection.URLReader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by FarisShatat on 11/16/15.
 */

class PlaceHolderFragment {
    public static class PlaceholderFragment extends Fragment {


        private GoogleMap mMap;

        private static final String ARG_SECTION_NUMBER = "section_number";
        private String content;
        private URLReader reader;
        List<Address> address;
        LinkedList<Float> latList = new LinkedList<>();
        LinkedList<Float> longList = new LinkedList<>();
        private List listofAllADdresses;
        private List listOfBusinessName;
        private List<String> businessesFoundOnMap = new ArrayList<>();
        private List<String> businessesAddressesFoundOnMap = new ArrayList<>();
        View rootView;


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            reader = new URLReader();
            listofAllADdresses = new ArrayList<>();
            if (rootView != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
            if (drnk.section.equals("near me")) {
                rootView = inflater.inflate(R.layout.activity_near_me, container, false);
                rootView.getLayoutParams().height = drnk.layoutHeight;
                setHasOptionsMenu(true);
                setUpMapIfNeeded();
            } else {
                rootView = inflater.inflate(R.layout.fragment_drnk, container, false);
            }
            return rootView;
        }


        @Override
        public void onPrepareOptionsMenu(Menu menu) {
            if (drnk.btnAddressClicked) {
                MenuItem register = menu.findItem(R.id.direction);
                register.setVisible(true);
                super.onPrepareOptionsMenu(menu);
            }

        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            if (drnk.btnAddressClicked) {
                inflater.inflate(R.menu.drnk, menu);
                MenuItem direction = menu.findItem(R.id.direction);
                direction.setVisible(true);
                super.onCreateOptionsMenu(menu, inflater);
            }
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.direction) {
                String uri = String.format(Locale.ENGLISH, "google.navigation:q=" + drnk.latitude + "," + drnk.longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onResume() {
            super.onResume();
            if (drnk.section.equals("near me")) {
                setUpMapIfNeeded();
            }
        }

        private void setUpMapIfNeeded() {
            if (mMap == null) {
                mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                        .getMap();
                if (mMap != null) {
                    setUpMap();
                }
            }

        }

        public void setUpMap() {
            new Thread(new Runnable() {
                public void run() {
                    retrieveData();

                }
            }).start();

        }

        public void retrieveData() {
            if (drnk.btnAddressClicked == false) {
                Parser parser;
                Business schedule = null;
                BusinessFormatter formatter = new BusinessFormatter();
                content = reader.getJSON("allAddress", "");
                parser = new Parser(content);
                try {
                    schedule = parser.parse("allAddresses");
                    System.out.println("getting all addresses");
                    listofAllADdresses = formatter.getAddress(schedule);
                    listOfBusinessName = formatter.getInfoForMap(schedule);
                    gecodeAddress();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            getActivity().runOnUiThread(new Runnable() {

                public void run() {
                    pinLocationsToMap();
                }
            });

        }

        public void gecodeAddress() {
            try {

                Geocoder selected_place_geocoder = new Geocoder(getContext(), Locale.getDefault());

                for (int i = 0; i < listofAllADdresses.size(); i++) {
                    address = selected_place_geocoder.getFromLocationName(String.valueOf(listofAllADdresses.get(i)), 2);

                    if (address.isEmpty()) {
                        if (listofAllADdresses.get(i).equals("1612 W Jackson St, Muncie")) {
                            address = selected_place_geocoder.getFromLocationName("1610 W Jackson St, Muncie", 2);
                        }
                        if (listofAllADdresses.get(i).equals("801 North Wheeling Avenue, Muncie")) {
                            address = selected_place_geocoder.getFromLocationName("803 North Wheeling Avenue, Muncie", 2);
                        }
                        if (listofAllADdresses.get(i).equals("409 N Martin St, Muncie")) {
                            address = selected_place_geocoder.getFromLocationName("411 North Martin Street, Muncie", 2);
                        }

                    }
//
                    for (int k = 0; k < address.size(); k++) {
                        Address location = address.get(k);
                        float latitude = (float) location.getLatitude();
                        float longitude = (float) location.getLongitude();
                        latList.add(latitude);
                        longList.add(longitude);
                        businessesFoundOnMap.add((String) listOfBusinessName.get(i));
                        businessesAddressesFoundOnMap.add((String) listofAllADdresses.get(i));
                    }

                }
                System.out.println(latList);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        private void pinLocationsToMap() {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mMap.clear();
            if (drnk.btnAddressClicked == true) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(drnk.latitude, drnk.longitude))
                        .title((String) drnk.listOfBusinesses.get(drnk.position))
                        .snippet((String) drnk.listOfAddress.get(drnk.position))
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_logo", 100, 100))));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(drnk.latitude, drnk.longitude), 15));

            } else {


                for (int i = 0; i < businessesAddressesFoundOnMap.size(); i++) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latList.get(i), longList.get(i)))
                            .title((String) businessesFoundOnMap.get(i))
                            .snippet((String) businessesAddressesFoundOnMap.get(i))
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_logo", 50, 50))));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latList.get(i), longList.get(i)), 15));
                }


            }
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(drnk.currentLatitude, drnk.currentLongitude))
                    .title("Current Location")
                    .icon(BitmapDescriptorFactory.defaultMarker()));
        }

        public Bitmap resizeMapIcons(String iconName, int width, int height) {
            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
            return resizedBitmap;
        }

        @Override
        public void onAttach(Context activity) {
            super.onAttach(activity);

            try {

                ((drnk) activity).onSectionAttached(
                        getArguments().getInt(ARG_SECTION_NUMBER));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}