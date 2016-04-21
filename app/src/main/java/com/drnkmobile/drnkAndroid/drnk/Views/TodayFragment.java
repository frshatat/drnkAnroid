package com.drnkmobile.drnkAndroid.drnk.Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.drnkmobile.drnkAndroid.app.R;
import com.drnkmobile.drnkAndroid.drnk.Customize.CustomCurrentSpecialListView;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.Business;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.BusinessFormatter;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.Parser;
import com.drnkmobile.drnkAndroid.drnk.Connection.URLReader;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TodayFragment extends Fragment {
    private List listOfBusinesses;
    private List listOfSpecials;
    private ArrayList<DownloadXMLAsyncTask> tasks;
    private URLReader reader;
    private String typeOfBusiness;
    private ListView list;
    private int positionForCellSelected;
    private View rootView;

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
        positionForCellSelected = getActivity().getIntent().getExtras().getInt("index");
        if (drnk.section == "stores") {
            typeOfBusiness = "liquorstores";
        } else {
            typeOfBusiness = "bars";
        }
        DownloadXMLAsyncTask task = new DownloadXMLAsyncTask();
        task.execute();
    }

    protected void updateDisplay() {
        CustomCurrentSpecialListView adapter = new CustomCurrentSpecialListView(rootView.getContext(), R.layout.list_text_view, listOfSpecials);
        list = (ListView) rootView.findViewById(R.id.listView3);
        list.setAdapter(adapter);
        //onTitleClick();
    }


    private class DownloadXMLAsyncTask extends AsyncTask<String, String,
            String> {
        @Override
        protected void onPreExecute() {

            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... input) {
            String content = null;
            content = reader.getJSON(typeOfBusiness, drnk.currentCity);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            Parser parser = new Parser(result);
            Business business = null;
            try {
                parser.positionForItemSelectedinTableView(positionForCellSelected);
                business = parser.parse("specials");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            BusinessFormatter formatter = new BusinessFormatter();
            listOfSpecials = formatter.getBusinessSpecials(business);

            updateDisplay();
            tasks.remove(this);

        }
    }
}
