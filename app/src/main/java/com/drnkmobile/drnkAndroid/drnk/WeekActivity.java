package com.drnkmobile.drnkAndroid.drnk;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.drnkmobile.drnkAndroid.app.R;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeekActivity extends Fragment {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    static List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private ArrayList<DownloadJSONAsyncTask> tasks;
    private URLReader reader;
    private String typeOfBusiness;
    private List listOfSpecials;
    private int number;
    private List<String> listSpecials;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_week, container, false);
        tasks = new ArrayList<DownloadJSONAsyncTask>();
        expListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Sunday");
        listDataHeader.add("Monday");
        listDataHeader.add("Tuesday");
        listDataHeader.add("Wednesday");
        listDataHeader.add("Thursday");
        listDataHeader.add("Friday");
        listDataHeader.add("Saturday");
        // preparing list data
        reader = new URLReader();
        requestData();
        return rootView;
    }

    private void requestData() {
        number = getActivity().getIntent().getExtras().getInt("index");
        DownloadJSONAsyncTask task = new DownloadJSONAsyncTask();
        task.execute();
    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {


        List<String> sundaySpecials = new ArrayList<>();
        sundaySpecials.add((String) listOfSpecials.get(0));
        List<String> mondaySpecials = new ArrayList<>();
        mondaySpecials.add((String) listOfSpecials.get(1));
        List<String> tuesdaySpecials = new ArrayList<>();
        tuesdaySpecials.add((String) listOfSpecials.get(2));
        List<String> wednsedaySpecials = new ArrayList<>();
        wednsedaySpecials.add((String) listOfSpecials.get(3));
        List<String> thursdaySpecials = new ArrayList<>();
        thursdaySpecials.add((String) listOfSpecials.get(4));
        List<String> fridaySpecials = new ArrayList<>();
        fridaySpecials.add((String) listOfSpecials.get(5));
        List<String> saturdaySpecials = new ArrayList<>();
        saturdaySpecials.add((String) listOfSpecials.get(6));


        listDataChild.put(listDataHeader.get(0), sundaySpecials);
        listDataChild.put(listDataHeader.get(1), mondaySpecials);
        listDataChild.put(listDataHeader.get(2), tuesdaySpecials);
        listDataChild.put(listDataHeader.get(3), wednsedaySpecials);
        listDataChild.put(listDataHeader.get(4), thursdaySpecials);
        listDataChild.put(listDataHeader.get(5), fridaySpecials);
        listDataChild.put(listDataHeader.get(6), saturdaySpecials);


    }

    protected void updateDisplay() {
        prepareListData();
        listAdapter = new ExpandableListAdapter(this.getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    private class DownloadJSONAsyncTask extends AsyncTask<String, String,
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
            content = reader.getJSON("bars");
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            Parser parser = new Parser(result);
            Special schedule = null;
            try {
                parser.passNumberIN(number);
                schedule = parser.parse("week");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SpecialFormatter formatter = new SpecialFormatter();
            listOfSpecials = formatter.getWeekSpecials(schedule);

            updateDisplay();
            tasks.remove(this);
//            if (tasks.size() == 0) {
//                progressBar.setVisibility(View.INVISIBLE);
//            }
        }
    }

}


