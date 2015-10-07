package com.drnkmobile.myapplication2.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class Parser {
    private Special.Builder builder;
    private String jsonFile;
    private  JSONObject obj;
    private JSONObject deal;
    private JSONArray ar;
    private int number;
    private String typeOfBusiness;
    public Parser(String jsonFile){
        this.jsonFile = jsonFile;
    }


    public Special parse(String typeOfBusiness) throws JSONException {
        if (!(jsonFile == null)) {
            ar = new JSONArray(jsonFile);
            this.typeOfBusiness=typeOfBusiness;
            parseInfo();
        } else {
            builder = Special.withBarName("Show not found!");
        }
        return builder.build();
    }
    public void parseInfo() {
        try {
            if(typeOfBusiness=="bars") {
                builder = Special.withBarName("Bars");

                for (int i = 0; i < ar.length(); i++) {
                    number = i;
                    obj = ar.getJSONObject(i);
                    findBusiness();
                }
            }
            else{
                    builder = Special.withBarName("liqourstores");
                    for (int i = 0; i < ar.length(); i++) {
                        number = i;
                        obj = ar.getJSONObject(i);
                        findBusiness();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
           // return null;
        }

    }

    private void findBusiness() throws JSONException {
        String businessName = obj.getString("company_name");
        BarTableInfo business = BarTableInfo.makeWithBarName(businessName);
        builder.addBusiness(business);
        if(typeOfBusiness=="bars") {
            findSpecials();
        }
        else{
            findLiquorSpecials();
        }
    }

    private void findSpecials() throws JSONException {
        JSONObject row = ar.getJSONObject(number);
        JSONArray special =  row.getJSONObject("deals").getJSONArray("friday");
        ArrayList<String>list = new ArrayList<String>();
        String n = null;
        for (int i = 0; i < special.length(); i++) {
            JSONObject s = special.getJSONObject(i);
             String name = s.getString("deal_name");

            list.add(name);
        //System.out.println("#######"+name);

      }
        n= list.get(0)+"\n"+list.get(1)+"\n" +list.get(2);
        BarTableInfo specials = BarTableInfo.makeWithBarSpecialName(n);
        builder.addSpecial(specials);

    }

    private void findLiquorSpecials() throws JSONException {
        JSONObject row = ar.getJSONObject(number);
        JSONArray special =  row.getJSONObject("deals").getJSONArray("everyday");
        ArrayList<String>list = new ArrayList<String>();
        String n = null;
        for (int i = 0; i < special.length(); i++) {
            JSONObject s = special.getJSONObject(i);
            String name = s.getString("deal_name");

            list.add(name);
            //System.out.println("#######"+name);

        }
        n= list.get(0)+"\n"+list.get(1)+"\n" +list.get(2);
        System.out.println(n);
        BarTableInfo specials = BarTableInfo.makeWithBarSpecialName(n);
        builder.addSpecial(specials);

    }
    }


