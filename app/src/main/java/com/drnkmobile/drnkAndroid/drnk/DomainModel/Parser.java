package com.drnkmobile.drnkAndroid.drnk.DomainModel;

import com.drnkmobile.drnkAndroid.drnk.Views.WeekFragment;
import com.drnkmobile.drnkAndroid.drnk.Views.drnk;
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
    private BusinessBuilder.Builder builder;
    private String jsonFile;
    private JSONObject obj;
    private JSONObject deal;
    private JSONArray jsonArray;
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


    public BusinessBuilder parse(String typeOfBusiness) throws JSONException {
        if (!(jsonFile == null)) {

            jsonArray = new JSONArray(jsonFile);

            this.typeOfBusiness = typeOfBusiness;
            parseInfo();
        } else {
            builder = BusinessBuilder.withBusinessName("Server Unavailable");
        }
        return builder.build();
    }

    public void parseInfo() {
        try {

            if (typeOfBusiness == "specials" || typeOfBusiness == "week") {
                builder = BusinessBuilder.withBusinessName("Bars");
                obj = jsonArray.getJSONObject(index);
                String businessName = obj.getString("company_name");

                Business business = Business.makeWithBusinessName(businessName);
                builder.addBusiness(business);
                if (typeOfBusiness == "week") {
                    parseBarSpecialForEachDayOfWeek();
                } else {
                    parseDetailViewSpecial();
                }
            } else {
                builder = BusinessBuilder.withBusinessName(" ");
                for (int i = 0; i < jsonArray.length(); i++) {
                    number = i;
                    obj = jsonArray.getJSONObject(i);
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
        Business business = Business.makeWithBusinessName(businessName);
        Business id = Business.makeId(businessId);
        builder.addBusiness(business);
        builder.addId(id);
        Business businessAddress = Business.makeWithAddress(address);
        builder.addAddress(businessAddress);
        Business businessPhoneNumber = Business.makePhoneNumber(phoneNumber);
        builder.addPhoneNumber(businessPhoneNumber);
        Business businessHours = Business.makewithBusinessHours(hours);
        builder.addBusinessHours(businessHours);
        if (typeOfBusiness == "bars") {
            findSpecials();
        } else if (typeOfBusiness.equals("liquorstores")) {
            findLiquorSpecials();
        }
    }

    private void findSpecials() throws JSONException {
        JSONObject row = jsonArray.getJSONObject(number);
        JSONArray special = row.getJSONObject("deals").getJSONArray(currentDay);
        ArrayList<String> featureList = new ArrayList<String>();
        ArrayList<String> extraSpecials = new ArrayList<String>();
        int countFeature=0;
        String deal_price = null;
        for (int i = 0; i < special.length(); i++) {
            JSONObject specialPrice = special.getJSONObject(i);
            String price = specialPrice.getString("price");
            if (price.equalsIgnoreCase("0.00")) {
                deal_price = " ";
            } else {
                deal_price = "$ " + price + " ";
            }
            int featured = specialPrice.getInt("featured");
            String business_special = null;
            String deal = specialPrice.getString("deal_name");

            business_special = deal_price + deal;
            if (featured == 1) {
                featureList.add(business_special);
                ++countFeature;
                if(countFeature==3)
                {
                    Business specials = Business.makeWithBusinessSpecialName(featureList.toString().replace("[", "").replace("]", "").replace(",", "\n\n"));
                    builder.addSpecial(specials);
                    break;
                }
            } else {
                featureList.add(" ");
                extraSpecials.add(business_special);
            }

        }
        checkInJsonFileForFeaturedDeals(featureList, extraSpecials);


    }

    private void checkInJsonFileForFeaturedDeals(ArrayList<String> featureList, ArrayList<String> extraSpecials) {
        String groupSpecials = null;
        if ((featureList.get(0).equals(" ") && featureList.get(1).equals(" ")) || (featureList.get(0).equals("") && featureList.get(1).equals(""))) {
            extraSpecials.remove(" ");
            extraSpecials.remove("");
            groupSpecials = displayNonFeaturedSpecials(extraSpecials);
        } else {
            //Remove all elements that contain only whitespace
            featureList.remove(" ");
            extraSpecials.remove(" ");
            extraSpecials.remove("");
            groupSpecials = checkForNumberedofFeaturedSpecialsAvailable(featureList, extraSpecials, groupSpecials);

        }

        Business specials = Business.makeWithBusinessSpecialName(groupSpecials);
        builder.addSpecial(specials);
    }

    private String checkForNumberedofFeaturedSpecialsAvailable(ArrayList<String> featureList, ArrayList<String> extraSpecials, String groupSpecials) {
        if (featureList.size() >= 3) {
            if (featureList.get(1).equals("  ") || featureList.get(2).equals("  ") || featureList.get(1).equals(" ")) {
                groupSpecials = featureList.get(0) + "\n" + extraSpecials.get(0) + "\n" + extraSpecials.get(1);

            } else {
                groupSpecials = featureList.get(0) + "\n" + featureList.get(1) + "\n" + featureList.get(2);


            }
        } else if (featureList.size() == 2) {
            if (extraSpecials.size() > 1) {
                groupSpecials = featureList.get(0) + "\n" + featureList.get(1) + "\n" + extraSpecials.get(0);
            } else {
                groupSpecials = featureList.get(0) + "\n" + featureList.get(1) + "\n";
            }
        } else if (featureList.size() == 1) {
            if (extraSpecials.size() >= 2) {
                groupSpecials = featureList.get(0) + "\n" + extraSpecials.get(0) + "\n" + extraSpecials.get(1);
            } else if (extraSpecials.size() == 1) {
                groupSpecials = featureList.get(0) + "\n" + extraSpecials.get(0) + "\n";

            } else {
                groupSpecials = featureList.get(0) + "\n";
            }
        }
        return groupSpecials;
    }

    private String displayNonFeaturedSpecials(ArrayList<String> extraSpecials) {
        String groupSpecials;
        if (extraSpecials.size() >= 3) {
            groupSpecials = extraSpecials.get(0) + "\n" + extraSpecials.get(1) + "\n" + extraSpecials.get(2);
        } else {
            groupSpecials = "There are no specials for today";
        }
        return groupSpecials;
    }

    private void parseDetailViewSpecial() throws JSONException {
        JSONObject row = jsonArray.getJSONObject(index);
        JSONArray special;
        special = checkTypeOfBusinessToParse(row);
        ArrayList<String> businessSpecialList = new ArrayList<String>();
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
                businessSpecialList.add(businessDeal);
            }


        }

        groupSpecials = businessSpecialList.toString().replace("[", "").replace("]", "").replace(",", "\n\n");
        businessSpecialList.clear();
        Business specials = Business.makeWithBusinessSpecialName(groupSpecials);
        builder.addSpecial(specials);


    }

    private JSONArray checkTypeOfBusinessToParse(JSONObject row) throws JSONException {
        JSONArray special;
        if (drnk.section.equals("stores")) {
            special = row.getJSONObject("deals").getJSONArray("everyday");
        } else {
            special = row.getJSONObject("deals").getJSONArray(currentDay);
        }
        return special;
    }


    private void findLiquorSpecials() throws JSONException {
        JSONObject row = jsonArray.getJSONObject(number);
        JSONArray special = row.getJSONObject("deals").getJSONArray("everyday");
        ArrayList<String> specialList = new ArrayList<String>();
        String groupSpecials = null;
        for (int i = 0; i < special.length(); i++) {
            JSONObject specialObject = special.getJSONObject(i);
            int featured = specialObject.getInt("featured");
            String deal_name = specialObject.getString("deal_name");
            JSONObject specialPrice = special.getJSONObject(i);
            String price = specialPrice.getString("price");
            String deal_price = null;
            if (!deal_name.equals("")) {
                if (price.equalsIgnoreCase("0.00")) {
                    deal_price = "";
                } else {
                    deal_price = "$ " + price + " ";
                }
                String businessDeal = deal_price + deal_name;
                specialList.add(businessDeal);
                if(specialList.size()==3) {
                    Business specials = Business.makeWithBusinessSpecialName(specialList.toString().replace("[", "").replace("]", "").replace(",", "\n\n"));
                    builder.addSpecial(specials);
                    break;
                }

            }
        }
            //groupSpecials = specialList.get(0) + "\n" + specialList.get(1) + "\n" + specialList.get(2);




    }

    private void parseBarSpecialForEachDayOfWeek() throws JSONException {

        JSONObject row = jsonArray.getJSONObject(index);
        JSONArray special = null;
        ArrayList<String> businessSpecialList = new ArrayList<String>();
        String groupSpecials = null;
        for (int i = 0; i < WeekFragment.listDataHeader.size(); i++) {
            special = row.getJSONObject("deals").getJSONArray(WeekFragment.listDataHeader.get(i).toLowerCase());

            if (!special.isNull(i)) {
                System.out.println("specials");
                for (int j = 0; j < special.length(); j++) {
                    JSONObject s = special.getJSONObject(j);
                    String deal_name = s.getString("deal_name");
                    JSONObject specialPrice = special.getJSONObject(j);
                    String price = specialPrice.getString("price");
                    String deal_price = null;
                    if (!deal_name.equals("")) {
                        if (price.equalsIgnoreCase("0.00")) {
                            deal_price = "";
                        } else {
                            deal_price = "$ " + price + " ";
                        }
                        String businessDeal = deal_price + deal_name;
                        businessSpecialList.add(businessDeal);
                    }


                }
            } else {
                System.out.println("No specials");
                businessSpecialList.add("Sorry no specials are available for this day.");
            }

            groupSpecials = businessSpecialList.toString().replace("[", "").replace("]", "").replace(",", "\n\n");
            Business specials = Business.makeWithBusinessSpecialName(groupSpecials);
            builder.addSpecial(specials);
            businessSpecialList.clear();
        }
    }


    public void positionForItemSelectedinTableView(int index) {
        this.index = index;
    }
}

