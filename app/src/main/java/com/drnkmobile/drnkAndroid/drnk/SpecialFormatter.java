package com.drnkmobile.drnkAndroid.drnk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class SpecialFormatter {

    List<String> businessList = new ArrayList<String>();
    List<String> specialList = new ArrayList<String>();
    List<String> businessIdList = new ArrayList<String>();
    List<String> addressList = new ArrayList<String>();
    List<String> weekSpecialsList = new ArrayList<String>();

    public List getBusinessData(Special tvSchedule) {
        if (tvSchedule.countBusinesses() > 0) {
            addElementsToList(tvSchedule);
        } else {
            businessList.add("Unknown Business");
        }
        return businessList;
    }

    public List getId(Special id){
        if (id.countId() > 0) {
            addId(id);
        } else {
            businessList.add("0000");
        }
        return businessIdList;
    }

    public List specials(Special special) {
        if (special.countSpecials() > 0) {
            addSpecials(special);
            return specialList;
        } else {
            specialList.add("Sorry we couldn't find any specials today.");
        }
        return specialList;
    }

    public List getWeekSpecials(Special special) {
        if (special.countWeekSpecials() > 0) {
            addSpecialsForWeek(special);
            return weekSpecialsList;
        } else {
            weekSpecialsList.add("Sorry we couldn't find any specials today.");
        }
        return weekSpecialsList;
    }
    public List getAddress(Special address){
        if (address.countAddress()>0){
            addAddress(address);
        }
        else{
            addressList.add("Unknown Location");
        }
        return addressList;
    }
    private void addAddress(Special address){
        for(int i = 0; i<address.countAddress();i++){
            addressList.add(formatAddress(address.getAddress(i)));
        }
    }
    private void addId(Special id){
        for (int i = 0; i<id.countId();i++){
            businessIdList.add(formatId(id.getId(i)));
        }
    }

    private void addElementsToList(Special business) {
        for (int i = 0; i < business.countBusinesses(); i++) {

            specialList.add(formatSpecialName(business.getSpecial(i)));
            businessList.add((formatBusinessName(business.getBusinessName(i))));
        }
    }

    private void addSpecialsForWeek(Special week){
        for(int i=0; i<week.countWeekSpecials(); i++){
            weekSpecialsList.add(formatSpecialName(week.getWeekSpecials(i)));
        }
    }

    protected void addSpecials(Special business) {
        for (int i = 0; i < business.countSpecials(); i++) {
            int episodeCount = i + 1;

            specialList.add(formatSpecialName(business.getSpecial(i)));
        }
    }


    private String formatBusinessName(BarTableInfo business) {
        return business.getTitle() + "\n";
    }
    private String formatAddress(BarTableInfo address) {
        return address.getTitle();
    }

    private String formatSpecialName(BarTableInfo special) {
        return "\n" + special.getTitle().toString();
    }
    private String formatId(BarTableInfo id){
        return id.getTitle();
    }

}
