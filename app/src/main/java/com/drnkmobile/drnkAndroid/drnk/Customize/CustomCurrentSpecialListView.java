package com.drnkmobile.drnkAndroid.drnk.Customize;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.drnkmobile.drnkAndroid.app.R;

import java.util.List;

/**
 * Created by FarisShatat on 12/4/15.
 */
public class CustomCurrentSpecialListView extends ArrayAdapter<String> {

    private Context context;
    private List<String> businessList;
    private List<String>specialList;
    private List<String>idList;
    private List<String>listOfAddress;

    public CustomCurrentSpecialListView(Context context, int resource, List<String> objects){
        super(context,resource,objects);
        this.context = context;
        this.specialList = objects;

    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        final String special = specialList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.customtoday_special_listview, null);

        TextView specials = (TextView)view.findViewById(R.id.todayspecial);
        Typeface face=Typeface.createFromAsset(view.getContext().getAssets(),"fonts/AvenirLTStd-Light.ttf");
        specials.setText(special);
        specials.setTypeface(face);



        return view;
    }
}