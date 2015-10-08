package com.drnkmobile.drnkAndroid.drnk;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.drnkmobile.drnkAndroid.app.R;

import java.util.List;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class CustomListView extends ArrayAdapter<String> {

    private Context context;
    private List<String> businessList;
    private List<String>specialList;

    public CustomListView(Context context, int resource, List<String> objects, List specialList){
        super(context,resource,objects);
        this.context = context;
        this.businessList = objects;
        this.specialList = specialList;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){
        String business = businessList.get(position);
        String special = specialList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_specials, null);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.ic_logo);
        TextView tv = (TextView)view.findViewById(R.id.textView2);
        TextView textView = (TextView)view.findViewById(R.id.textView);
        tv.setText(business);
        textView.setText(special);
        return view;
    }
}

