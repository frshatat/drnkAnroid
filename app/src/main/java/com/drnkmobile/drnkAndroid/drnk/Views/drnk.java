package com.drnkmobile.drnkAndroid.drnk.Views;

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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.drnkmobile.drnkAndroid.app.R;
import com.drnkmobile.drnkAndroid.drnk.Customize.CustomListView;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.BusinessBuilder;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.BusinessFormatter;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.Parser;
import com.drnkmobile.drnkAndroid.drnk.Connection.LocationService;
import com.drnkmobile.drnkAndroid.drnk.Customize.RoundedImageView;
import com.drnkmobile.drnkAndroid.drnk.Connection.URLReader;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class drnk extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SwipeRefreshLayout.OnRefreshListener {

    LocationManager locationManager;
    String provider;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    static List listOfBusinesses;
    private List listOfSpecials;
    private List listOfId;
    private ArrayList<DownloadXMLAsyncTask> tasks;
    private URLReader reader;
    private String typeOfBusiness;
    private ListView businessListView;
    public static float latitude;
    public static float longitude;
    static float currentLatitude;
    static float currentLongitude;

    static boolean btnAddressClicked = false;
    static int position;
    private CharSequence mTitle;
    public static CharSequence section;
    LocationService gps;
    static List listOfAddress;
    private android.support.v7.widget.Toolbar toolbar;

    private CustomListView adapter;
    static int toolbarHeight;
    private RelativeLayout layout;
    static int layoutHeight;
    private List listofBusinessHours;
    private List listofPhoneNumbers;
    TextView networkMessageTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static String currentCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drnk);

        layout = (RelativeLayout) findViewById(R.id.container);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        networkMessageTextView = (TextView) findViewById(R.id.server_message);
        networkMessageTextView.setVisibility(View.INVISIBLE);

        reader = new URLReader();
        tasks = new ArrayList<DownloadXMLAsyncTask>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        businessListView = (ListView) findViewById(R.id.listView2);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        //swipeRefreshLayout.setProgressBackgroundColorSchemeColor();
        swipeRefreshLayout.setColorSchemeResources(R.color.accentColor, R.color.accentColor,
                R.color.accentColor, R.color.accentColor);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        checkForConnection();
                                    }
                                }
        );


    }

    @Override
    public void onRefresh() {
        checkForConnection();
    }

    @Override
    public void onBackPressed() {

        if (btnAddressClicked) {
            if (typeOfBusiness.equals("bars")) {
                btnAddressClicked = false;
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceHolderFragment.PlaceholderFragment.newInstance(1))
                        .commit();
            }
            if (typeOfBusiness.equals("liquorstores")) {
                btnAddressClicked = false;
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceHolderFragment.PlaceholderFragment.newInstance(2))
                        .commit();
            }

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toolbarHeight = toolbar.getHeight();
        layoutHeight = layout.getHeight();


        return true;
    }

    private void getLocation() throws IOException {
        gps = new LocationService(drnk.this);

        if (gps.canGetLocation()) {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());


            currentLatitude = (float) gps.getLatitude();
            currentLongitude = (float) gps.getLongitude();
            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);

            if (!addresses.isEmpty()) {
                for (int i = 0; i < addresses.size(); i++) {
                    Address location = addresses.get(i);
                    currentCity = location.getLocality();
                }

                Toast.makeText(getApplicationContext(), "Your City is: " + currentCity, Toast.LENGTH_LONG).show();
            }
        } else {
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        btnAddressClicked = false;

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceHolderFragment.PlaceholderFragment.newInstance(position + 1))
                .commit();

    }


    public void onSectionAttached(int number) throws IOException {

        switch (number) {
            case 1:
                mTitle = "bars";
                typeOfBusiness = "bars";
                swipeRefreshLayout.setRefreshing( true );
                swipeRefreshLayout.setEnabled( true );
                section = mTitle;
                btnAddressClicked = true;
                getLocation();

                checkForConnection();
                break;
            case 2:
                mTitle = "stores";
                typeOfBusiness = "liquorstores";
                swipeRefreshLayout.setRefreshing( true );
                swipeRefreshLayout.setEnabled( true );
                section = mTitle;
                btnAddressClicked = true;
                getLocation();
                businessListView.setAdapter(null);
                checkForConnection();

                break;
            case 3:
                mTitle = "near me";
                section = mTitle;
                swipeRefreshLayout.setRefreshing( false );
                swipeRefreshLayout.setEnabled( false );
                break;
        }
    }

    public void restoreActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void checkForConnection() {

        if (isOnline()) {
            requestData();
        } else {
            Toast.makeText(this, "Network isn't available",
                    Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);


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


    public void findSpecificBusinessLocationWhenUserClicksOnAddress(View view) {
        try {
            btnAddressClicked = true;
            View parentRow = (View) view.getParent();

            position = businessListView.getPositionForView(parentRow);
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
                if (listOfAddress.get(position).equals("409 N Martin St, Muncie")) {
                    address = selected_place_geocoder.getFromLocationName("411 North Martin Street, Muncie", 2);
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
        adapter = new CustomListView(this, R.layout.items_main_tableview, listOfBusinesses, listOfSpecials, listOfId, listOfAddress);
        if (businessListView != null) {
            businessListView.setAdapter(adapter);

            onItemClicked();
        }

    }


    public void onItemClicked() {

        businessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent resultActivityIntent = new Intent(getApplicationContext(),
                        BusinessActivity.class);
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
                swipeRefreshLayout.setRefreshing(true);
            }
            tasks.add(this);


        }

        @Override
        protected String doInBackground(String... input) {
            String content = null;

            if (section == "near me") {
                content = reader.getJSON(typeOfBusiness, currentCity);
            } else {
                content = reader.getJSON(typeOfBusiness, currentCity);
            }
            return content;
        }

        @Override
        protected void onPostExecute(String result) {


            if (result.contains("<!DOCTYPE html") || result.equals("Server Unavailable") || result.equals(null)) {
                System.out.println("Something");
                networkMessageTextView.setText("Server Unavailable");
                networkMessageTextView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);

            } else {

                Parser parser = new Parser(result);
                BusinessBuilder schedule = null;
                try {
                    schedule = parser.parse(typeOfBusiness);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BusinessFormatter formatter = new BusinessFormatter();

                listOfBusinesses = formatter.getBusinessData(schedule);
                listOfSpecials = formatter.getBusinessSpecials(schedule);
                listOfId = formatter.getId(schedule);
                listOfAddress = formatter.getAddress(schedule);
                listofBusinessHours = formatter.getBusinessHours(schedule);
                listofPhoneNumbers = formatter.getPhoneNumber(schedule);
                updateDisplay();


            }
            tasks.remove(this);
            if (tasks.size() == 0) {
                swipeRefreshLayout.setRefreshing(false);
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
    protected void onDestroy() {

        // The activity is about to be destroyed.
        super.onDestroy();

        // Stop method tracing that the activity started during onCreate()
        android.os.Debug.stopMethodTracing();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

}