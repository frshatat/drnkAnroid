package com.drnkmobile.drnkAndroid.drnk;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
    private JSONObject obj;
    private JSONObject deal;
    private JSONArray ar;
    private int number;
    private int index;
    private String typeOfBusiness;
    private String currentDay;
    private String businessId;

    public Parser(String jsonFile) {
        this.jsonFile = jsonFile;
        LocalDate date = LocalDate.now();
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE");
        currentDay = date.toString(fmt);
        currentDay = currentDay.toLowerCase();

    }


    public Special parse(String typeOfBusiness) throws JSONException {
        if (!(jsonFile == null)) {
            ar = new JSONArray(jsonFile);
            this.typeOfBusiness = typeOfBusiness;
            parseInfo();
        } else {
            builder = Special.withBarName("Show not found!");
        }
        return builder.build();
    }

    public void parseInfo() {
        try {
            if (typeOfBusiness == "bars") {
                builder = Special.withBarName("Bars");

                for (int i = 0; i < ar.length(); i++) {
                    number = i;
                    obj = ar.getJSONObject(i);
                    findBusiness();
                }
            }
            if (typeOfBusiness == "allAddresses") {
                builder = Special.withBarName("Addresses");

                for (int i = 0; i < ar.length(); i++) {
                    number = i;
                    obj = ar.getJSONObject(i);
                    findBusiness();
                }
            }
            if (typeOfBusiness == "specials" || typeOfBusiness == "week") {
                builder = Special.withBarName("Bars");
                obj = ar.getJSONObject(index);
                String businessName = obj.getString("company_name");

                BarTableInfo business = BarTableInfo.makeWithBarName(businessName);
                builder.addBusiness(business);
                if (typeOfBusiness == "week") {
                    parseSpecialForWeek();
                } else {
                    findCurrentSpecials();
                }
            } else {
                builder = Special.withBarName("liquorstores");
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
        businessId = obj.getString("id");
        String street = obj.getString("company_street");
        String city = obj.getString("company_city");
        String address = street + ", " + city;
        String hours = obj.getString(currentDay + "_hours");
        String phoneNumber = obj.getString("company_phone");
        BarTableInfo business = BarTableInfo.makeWithBarName(businessName);
        BarTableInfo id = BarTableInfo.makeId(businessId);
        builder.addBusiness(business);
        builder.addId(id);
        BarTableInfo businessAddress = BarTableInfo.makeWithAddress(address);
        builder.addAddress(businessAddress);
        BarTableInfo businessPhoneNumber = BarTableInfo.makePhoneNumber(phoneNumber);
        builder.addPhoneNumber(businessPhoneNumber);
        BarTableInfo businessHours = BarTableInfo.makewithBusinessHours(hours);
        builder.addBusinessHours(businessHours);
        if (typeOfBusiness == "bars") {
            findSpecials();
        } else if (typeOfBusiness.equals("liquorstores")) {
            findLiquorSpecials();
        }
    }

    private void findSpecials() throws JSONException {
        JSONObject row = ar.getJSONObject(number);
        JSONArray special = row.getJSONObject("deals").getJSONArray(currentDay);
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> idList = new ArrayList<String>();
        String groupSpecials = null;
        String deal_price = null;
        for (int i = 0; i < special.length(); i++) {
            JSONObject specialPrice = special.getJSONObject(i);
            String price = specialPrice.getString("price");
            if (price.equalsIgnoreCase("0.00")) {
                deal_price = " ";
            } else {
                deal_price = "$ " + price + " ";
            }
            String deal_name = specialPrice.getString("deal_name");
            String deal = deal_price + deal_name;
            list.add(deal);

        }
        if (list.get(0).equals(" ")) {
            groupSpecials = "There are not specials for today";
        } else {
            if (list.size() >= 3) {
                groupSpecials = list.get(0) + "\n" + list.get(1) + "\n" + list.get(2);
            } else if (list.size() == 2) {
                groupSpecials = list.get(0) + "\n" + list.get(1) + "\n";
            } else if (list.size() == 1) {
                groupSpecials = list.get(0) + "\n";
            }
        }
        BarTableInfo specials = BarTableInfo.makeWithBarSpecialName(groupSpecials);
        builder.addSpecial(specials);


    }

    private void findCurrentSpecials() throws JSONException {
        JSONObject row = ar.getJSONObject(index);
        JSONArray special;
        if (drnk.section.equals("stores")) {
            special = row.getJSONObject("deals").getJSONArray("everyday");
        } else {
            special = row.getJSONObject("deals").getJSONArray(currentDay);
        }
        ArrayList<String> list = new ArrayList<String>();
        String groupSpecials = null;
        for (int i = 0; i < special.length(); i++) {
            JSONObject deal = special.getJSONObject(i);
            String deal_name = deal.getString("deal_name");
            JSONObject specialPrice = special.getJSONObject(i);
            String price = specialPrice.getString("price");
            String deal_price = null;
            if (deal_name.equals("")) {

            } else {
                if (price.equalsIgnoreCase("0.00")) {
                    deal_price = " ";
                } else {
                    deal_price = "$ " + price + " ";
                }
                String businessDeal = deal_price + deal_name;
                list.add(businessDeal);
            }


        }

        groupSpecials = list.toString().replace("[", "").replace("]", "").replace(",", "\n\n");

        BarTableInfo specials = BarTableInfo.makeWithBarSpecialName(groupSpecials);
        builder.addSpecial(specials);

    }


    private void findLiquorSpecials() throws JSONException {
        JSONObject row = ar.getJSONObject(number);
        JSONArray special = row.getJSONObject("deals").getJSONArray("everyday");
        ArrayList<String> specialList = new ArrayList<String>();
        String groupSpecials = null;
        for (int i = 0; i < special.length(); i++) {
            JSONObject specialObject = special.getJSONObject(i);
            String deal_name = specialObject.getString("deal_name");
            JSONObject specialPrice = special.getJSONObject(i);
            String price = specialPrice.getString("price");
            String deal_price = null;
            if (deal_name.equals("")) {

            } else {
                if (price.equalsIgnoreCase("0.00")) {
                    deal_price = "";
                } else {
                    deal_price = "$ " + price + " ";
                }
                String businessDeal = deal_price + deal_name;
                specialList.add(businessDeal);
            }


        }
        groupSpecials = specialList.get(0) + "\n" + specialList.get(1) + "\n" + specialList.get(2);

        BarTableInfo specials = BarTableInfo.makeWithBarSpecialName(groupSpecials);
        builder.addSpecial(specials);

    }

    private void parseSpecialForWeek() throws JSONException {

        JSONObject row = ar.getJSONObject(index);
        JSONArray special = null;
        ArrayList<String> list = new ArrayList<String>();
        String groupSpecials = null;
        for (int i = 0; i < WeekActivity.listDataHeader.size(); i++) {
            special = row.getJSONObject("deals").getJSONArray(WeekActivity.listDataHeader.get(i).toLowerCase());

            for (int j = 0; j < special.length(); j++) {
                JSONObject s = special.getJSONObject(j);
                String deal_name = s.getString("deal_name");
                JSONObject specialPrice = special.getJSONObject(j);
                String price = specialPrice.getString("price");
                String deal_price = null;
                if (deal_name.equals("")) {

                } else {
                    if (price.equalsIgnoreCase("0.00")) {
                        deal_price = "";
                    } else {
                        deal_price = "$ " + price + " ";
                    }
                    String businessDeal = deal_price + deal_name;
                    list.add(businessDeal);
                }


            }

            groupSpecials = list.toString().replace("[", "").replace("]", "").replace(",", "\n\n");
            BarTableInfo specials = BarTableInfo.makeWithWeeklySpecials(groupSpecials);
            builder.addWeekSpecials(specials);
            list.clear();
        }
    }


    public void passNumberIN(int index) {
        this.index = index;
    }
}


