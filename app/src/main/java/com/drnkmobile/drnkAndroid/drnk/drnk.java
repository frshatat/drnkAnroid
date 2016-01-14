package com.drnkmobile.drnkAndroid.drnk;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.drnkmobile.drnkAndroid.app.R;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class drnk extends AppCompatActivity
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
    private CharSequence mTitle;
    static CharSequence section;
    LocationService gps;
    static List listOfAddress;
    private android.support.v7.widget.Toolbar toolbar;
    private ProgressBar progressBar;
    private CustomListView adapter;
    static int toolbarHeight;
    private RelativeLayout layout;
    static int layoutHeight;
    private List listofBusinessHours;
    private List listofPhoneNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drnk);
        layout = (RelativeLayout) findViewById(R.id.container);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
//        InfoFragment.fragment = "drnk";
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        reader = new URLReader();
        tasks = new ArrayList<DownloadXMLAsyncTask>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        reader = new URLReader();
        tasks = new ArrayList<DownloadXMLAsyncTask>();
        System.out.println("OnCreate was called");


    }


    @Override
    public void onBackPressed() {

        if(buttonClicked) {
            if(typeOfBusiness.equals("bars")){
                buttonClicked = false;
//                InfoFragment.fragment = "drnk";
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceHolderFragment.PlaceholderFragment.newInstance(1))
                        .commit();
            }
            if (typeOfBusiness.equals("liquorstores")){
                buttonClicked = false;
//                InfoFragment.fragment = "drnk";
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceHolderFragment.PlaceholderFragment.newInstance(2))
                        .commit();
            }
//            Log.d("CDA", "onBackPressed Called");
//            Intent setIntent = new Intent(this, drnk.class);
//            setIntent.addCategory(Intent.CATEGORY_HOME);
//            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(setIntent);
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toolbarHeight = toolbar.getHeight();
        layoutHeight = layout.getHeight();
        return true;
    }

    private void getLocation() {
        gps = new LocationService(drnk.this);

        if (gps.canGetLocation()) {
            currentLatitude = (float) gps.getLatitude();
            currentLongitude = (float) gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        buttonClicked = false;
        InfoFragment.fragment = "drnk";
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceHolderFragment.PlaceholderFragment.newInstance(position + 1))
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

                checkForConnection();
                break;
            case 2:
                mTitle = "stores";
                typeOfBusiness = "liquorstores";
                section = mTitle;
                buttonClicked = true;
                getLocation();
                list.setAdapter(null);
                checkForConnection();

                break;
            case 3:
                mTitle = "near me";
                section = mTitle;
                break;
        }
    }

    public void restoreActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void checkForConnection() {

        if (isOnline()) {
            requestData();
        } else {
            Toast.makeText(this, "Network isn't available",
                    Toast.LENGTH_LONG).show();
        }

    }

    protected boolean isOnline() {
        ConnectivityManager connectionManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectionManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
            address = selected_place_geocoder.getFromLocationName(String.valueOf(listOfAddress.get(position)), 2);
            System.out.println(address);

            if (address.isEmpty()) {
                if (listOfAddress.get(position).equals("1612 W Jackson St, Muncie")) {
                    address = selected_place_geocoder.getFromLocationName("1610 W Jackson St, Muncie", 2);
                }
                if (listOfAddress.get(position).equals("801 North Wheeling Avenue, Muncie")) {
                    address = selected_place_geocoder.getFromLocationName("803 North Wheeling Avenue, Muncie", 2);
                }

            }
            for (int i = 0; i < address.size(); i++) {
                Address location = address.get(i);
                latitude = (float) location.getLatitude();
                longitude = (float) location.getLongitude();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceHolderFragment.PlaceholderFragment.newInstance(2 + 1))
                .commit();
    }

    protected void updateDisplay() {
        adapter = new CustomListView(this, R.layout.item_specials, listOfBusinesses, listOfSpecials, listOfId, listOfAddress);
        list = (ListView) findViewById(R.id.listView2);
        if (list != null) {
            list.setAdapter(adapter);

            onTitleClick();
        }

    }


    public void onTitleClick() {


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent resultActivityIntent = new Intent(getApplicationContext(),
                        SpecialActivity.class);
                RoundedImageView transition = (RoundedImageView) findViewById(R.id.imageView);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(drnk.this, transition, "profile");
                int image = adapter.generateImage((String) listOfId.get(position));
                int index = position;

                resultActivityIntent.putExtra("image", image);
                resultActivityIntent.putExtra("index", index);
                resultActivityIntent.putExtra("businessHours", String.valueOf(listofBusinessHours.get(position)));
                resultActivityIntent.putExtra("businessPhoneNumber", String.valueOf(listofPhoneNumbers.get(position)));
                resultActivityIntent.putExtra("businessAddress", String.valueOf(listOfAddress.get(position)));
                resultActivityIntent.putExtra("businessName", String.valueOf(listOfBusinesses.get(position)));
                startActivity(resultActivityIntent, options.toBundle());

            }
        });
    }


    private class DownloadXMLAsyncTask extends AsyncTask<String, String,
            String> {
        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... input) {
            String content = null;
            SpecialFormatter formatter = new SpecialFormatter();
            Special schedule = null;

            if (section == "near me") {
                content = reader.getJSON(typeOfBusiness);
            } else {
                content = reader.getJSON(typeOfBusiness);
            }
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
            listOfSpecials = formatter.getBusinessSpecials(schedule);
            listOfId = formatter.getId(schedule);
            listOfAddress = formatter.getAddress(schedule);
            listofBusinessHours = formatter.getBusinessHours(schedule);
            listofPhoneNumbers = formatter.getPhoneNumber(schedule);
            updateDisplay();

            tasks.remove(this);
            if (tasks.size() == 0) {
                progressBar.setVisibility(View.INVISIBLE);
            }
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
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


}