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
public class CustomListView extends ArrayAdapter<String> {

    private Context context;
    private List<String> businessList;
    private List<String>specialList;
    private List<String>idList;
    private List<String>listOfAddress;

    public CustomListView(Context context, int resource, List<String> objects, List specialList, List idList, List listOfAddress){
        super(context,resource,objects);
        this.context = context;
        this.businessList = objects;
        this.specialList = specialList;
        this.idList = idList;
        this.listOfAddress=listOfAddress;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){
        String business = businessList.get(position);
        final String special = specialList.get(position);
        String address = listOfAddress.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_specials, null);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        image.setImageResource(generateImage(idList.get(position)));

        TextView businessName = (TextView)view.findViewById(R.id.textView2);
        TextView specials = (TextView)view.findViewById(R.id.textView);
        Typeface face=Typeface.createFromAsset(view.getContext().getAssets(),"fonts/AvenirLTStd-Light.ttf");
        businessName.setText(business);
        specials.setText(special);
        businessName.setTypeface(face);
        specials.setTypeface(face);
        TextView addressText = (TextView)view.findViewById(R.id.address);
        addressText.setText(address);


        return view;
    }


    public int generateImage(String id){

        String imageString = id;
        int businessImage = R.drawable.ic_logo;

        switch (imageString) {

            case "4":
                businessImage = R.drawable.brothers;
                break;

            case "5":

                businessImage = R.drawable.cleos;
                break;

            case "11":

                businessImage = R.drawable.be_here_now;
                break;

            case "12":

                businessImage = R.drawable.the_chug;
                break;

            case "13":

                businessImage = R.drawable.scottys;
                break;

            case "3":

                businessImage = R.drawable.friendly_package;
                break;

            case "6":

                businessImage = R.drawable.munice_liquors;
                break;

            case "0000":

                businessImage = R.drawable.ic_logo;
                break;

        }

        return businessImage;

    }
}

