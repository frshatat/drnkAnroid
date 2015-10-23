package com.drnkmobile.drnkAndroid.drnk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.drnkmobile.drnkAndroid.app.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class drnk extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    LocationManager locationManager;
    String provider;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    static List listOfBusinesses;
    private List listOfSpecials;
    private List listOfId;
    private ArrayList<DownloadXMLAsyncTask> tasks;
    private URLReader reader;
    private String typeOfBusiness;
    private ListView list;
    static float latitude;
    static float longitude;
    static float currentLatitude;
    static float currentLongitude;
    static ArrayList<Float> latList = new ArrayList<>();
    static ArrayList<Float> longList = new ArrayList<>();
    static boolean buttonClicked = false;
    static int position;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    static CharSequence section;
    LocationService gps;
    static List listOfAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drnk);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();


        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        reader = new URLReader();
        tasks = new ArrayList<DownloadXMLAsyncTask>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        reader = new URLReader();
        tasks = new ArrayList<DownloadXMLAsyncTask>();


    }


    private void getLocation() {
        gps = new LocationService(drnk.this);

        if (gps.canGetLocation()) {

            currentLatitude = (float) gps.getLatitude();
            currentLongitude = (float) gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        buttonClicked = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();

    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "bars";
                typeOfBusiness = "bars";
                section = mTitle;
                buttonClicked = true;
                getLocation();
                requestData();
                break;
            case 2:
                mTitle = "stores";
                typeOfBusiness = "liquorstores";
                section = mTitle;
                buttonClicked = true;
                getLocation();
                requestData();
                break;
            case 3:
                mTitle = "near me";
                requestData();
                section = mTitle;
                if (buttonClicked == false) {
                    gecodeAddress();
                }
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void requestData() {
        DownloadXMLAsyncTask task = new DownloadXMLAsyncTask();
        task.execute();
    }

    public void findBusiness(View view) {
        try {
            buttonClicked = true;
            View parentRow = (View) view.getParent();

            position = list.getPositionForView(parentRow);
            Geocoder selected_place_geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = null;
            address = selected_place_geocoder.getFromLocationName(String.valueOf(listOfAddress.get(position) + " IN"), 5);
            if (address == null) {
                System.out.println("Nothing");
            } else {
                Address location = address.get(0);
                latitude = (float) location.getLatitude();
                longitude = (float) location.getLongitude();
//                latList.add(latitude);
//                longList.add(longitude);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(2 + 1))
                .commit();
    }

    protected void updateDisplay() {
        CustomListView adapter = new CustomListView(this, R.layout.item_specials, listOfBusinesses, listOfSpecials, listOfId, listOfAddress);
        list = (ListView) findViewById(R.id.listView2);
        list.setAdapter(adapter);
        onTitleClick();

    }

    public void gecodeAddress() {
        try {

            Geocoder selected_place_geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> address = null;
            for (int i = 0; i < listOfAddress.size(); i++) {
                System.out.println(listOfAddress.size());
                address = selected_place_geocoder.getFromLocationName(String.valueOf(listOfAddress.get(i) + " IN"), 5);
                if (address == null) {
                    System.out.println("Nothing");
                } else {
                    Address location = address.get(0);
                    latitude = (float) location.getLatitude();
                    longitude = (float) location.getLongitude();
                    latList.add(latitude);
                    longList.add(longitude);


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//         FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, PlaceholderFragment.newInstance(2 + 1))
//                .commit();
    }


    public void onTitleClick() {

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent resultActivityIntent = new Intent(getApplicationContext(),
                        SpecialActivity.class);
                String businessName = parent.getItemAtPosition(position).toString();
                int a = position;
                System.out.println(a);
                resultActivityIntent.putExtra("a", a);


                startActivity(resultActivityIntent);
            }
        });
    }


    private class DownloadXMLAsyncTask extends AsyncTask<String, String,
            String> {
        @Override
        protected void onPreExecute() {
//            if (tasks.size() == 0) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... input) {
            String content = null;
            content = reader.getJSON(typeOfBusiness);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            Parser parser = new Parser(result);
            Special schedule = null;
            try {
                schedule = parser.parse(typeOfBusiness);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SpecialFormatter formatter = new SpecialFormatter();
            listOfBusinesses = formatter.getBusinessData(schedule);
            listOfSpecials = formatter.specials(schedule);
            listOfId = formatter.getId(schedule);
            listOfAddress = formatter.getAddress(schedule);
            updateDisplay();
            tasks.remove(this);
//            if (tasks.size() == 0) {
//                progressBar.setVisibility(View.INVISIBLE);
//            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.drnk, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private GoogleMap mMap;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
            View rootView;
            if (section.equals("near me")) {
                rootView = inflater.inflate(R.layout.activity_near_me, container, false);
                setUpMapIfNeeded();
            } else {
                rootView = inflater.inflate(R.layout.fragment_drnk, container, false);
            }
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            if (section.equals("near me")) {
                setUpMapIfNeeded();
            }
        }

        private void setUpMapIfNeeded() {
            // Do a null check to confirm that we have not already instantiated the map.
            if (mMap == null) {
                // Try to obtain the map from the SupportMapFragment.
                mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                        .getMap();
                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                    setUpMap();
                }
            }

        }

        public void setUpMap() {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.clear();
            if (buttonClicked == true) {
//                for (int i = 0; i < latList.size(); i++) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                        .title((String) listOfBusinesses.get(position))
                        .snippet((String) listOfAddress.get(position))
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_logo", 100, 100))));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));

                //  }
            } else {

                for (int i = 0; i < listOfAddress.size(); i++) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latList.get(i), longList.get(i)))
                            .title((String) listOfBusinesses.get(i))
                            .snippet((String) listOfAddress.get(i))
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_logo", 100, 100))));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
                }
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(currentLatitude, currentLongitude))
                        .title("Current Location")
                        .icon(BitmapDescriptorFactory.defaultMarker()));
            }

        }

        public Bitmap resizeMapIcons(String iconName, int width, int height) {
            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
            return resizedBitmap;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            ((drnk) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

        }
    }

}
