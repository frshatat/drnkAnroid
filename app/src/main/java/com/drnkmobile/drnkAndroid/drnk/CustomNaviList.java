package com.drnkmobile.drnkAndroid.drnk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
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
public class CustomNaviList extends ArrayAdapter<String> {

    private Context context;
    private List<String> dataList;


    public CustomNaviList(Context context, int layoutResID, int text1, List<String> dataList) {
        super(context, layoutResID, text1, dataList);
        this.context=context;
        this.dataList=dataList;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){
        String tabName = dataList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.navi_custom_list, null);
        ImageView image = (ImageView) view.findViewById(R.id.navigationdrawerimage);
        image.setImageResource(R.drawable.ic_logo);
        TextView tv = (TextView)view.findViewById(R.id.navigationdrawertext);
        tv.setText(tabName);
        Typeface face=Typeface.createFromAsset(view.getContext().getAssets(),"fonts/AvenirLTStd-Light.ttf");
        tv.setTypeface(face);

        return view;
    }



}