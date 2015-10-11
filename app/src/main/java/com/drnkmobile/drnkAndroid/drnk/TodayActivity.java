package com.drnkmobile.drnkAndroid.drnk;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.drnkmobile.drnkAndroid.app.R;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TodayActivity extends Fragment {
    private List listOfBusinesses;
    private List listOfSpecials;
    private ArrayList<DownloadXMLAsyncTask> tasks;
    private URLReader reader;
    private String typeOfBusiness;
    private ListView list;
    private int number;
    private  View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_today, container, false);
        reader = new URLReader();
        tasks = new ArrayList<DownloadXMLAsyncTask>();

        requestData();
        return rootView;
    }

    private void requestData() {
       // number = this.getArguments().getInt("index");
        number = getActivity().getIntent().getExtras().getInt("a");
        typeOfBusiness = "bars";
        DownloadXMLAsyncTask task = new DownloadXMLAsyncTask();
        task.execute();
    }

    protected void updateDisplay() {
        ListAdapter adapter = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, listOfSpecials);
        list = (ListView) rootView.findViewById(R.id.listView3);
        list.setAdapter(adapter);
        //onTitleClick();
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
                parser.passNumberIN(number);
                schedule = parser.parse("specials");
                //parser.findTodaySpecials(number);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SpecialFormatter formatter = new SpecialFormatter();
            //listOfBusinesses = formatter.getBusinessData(schedule);

            listOfSpecials = formatter.specials(schedule);

            updateDisplay();
            tasks.remove(this);
//            if (tasks.size() == 0) {
//                progressBar.setVisibility(View.INVISIBLE);
//            }
        }
    }
}
