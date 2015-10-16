package com.drnkmobile.drnkAndroid.drnk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
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

import java.util.ArrayList;
import java.util.List;


public class drnk extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    LocationManager locationManager;
    String provider;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private List listOfBusinesses;
    private List listOfSpecials;
    private ArrayList<DownloadXMLAsyncTask> tasks;
    private URLReader reader;
    private String typeOfBusiness;
    private ListView list;
    static float latitude;
    static float longitude;
    View view;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    static CharSequence section;
    LocationService gps;

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
        provider = locationManager.getBestProvider(new Criteria(),false);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        reader = new URLReader();
        tasks = new ArrayList<DownloadXMLAsyncTask>();


    }
    private void getLocation() {
        gps = new LocationService(drnk.this);

        if (gps.canGetLocation()) {

             latitude = (float) gps.getLatitude();
             longitude = (float) gps.getLongitude();

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
                section=mTitle;
                getLocation();
                requestData();
                break;
            case 2:
                mTitle = "stores";
                typeOfBusiness = "liquorstores";
                section=mTitle;
                getLocation();
                requestData();
                break;
            case 3:
                 mTitle = "near me";
                listOfBusinesses.clear();
                listOfSpecials.clear();
                 section=mTitle;
//                Intent in = new Intent(getApplicationContext(), NearMe.class);
//                startActivity(in);
               // getFragmentManager().popBackStack();
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

    protected void updateDisplay() {
        CustomListView adapter = new CustomListView(this, R.layout.item_specials, listOfBusinesses, listOfSpecials);
         list = (ListView) findViewById(R.id.listView2);
        list.setAdapter(adapter);
        onTitleClick();
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




    private void openNotificationActivity(int position) {
        Intent resultActivityIntent = new Intent(getApplicationContext(),
                SpecialActivity.class);
        startActivity(resultActivityIntent);
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
            if(section.equals("near me")){
                rootView = inflater.inflate(R.layout.activity_near_me, container, false);
                setUpMapIfNeeded();
            }
            else {
                rootView = inflater.inflate(R.layout.fragment_drnk, container, false);
            }
            return rootView;
        }
        @Override
        public void onResume() {
            super.onResume();
            if(section.equals("near me")) {
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
        private void setUpMap() {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.addMarker(new MarkerOptions().position(new LatLng(48.871387, 2.354951)).title("Current location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.871387, 2.354951), 15));
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            ((drnk) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

        }
    }

}
