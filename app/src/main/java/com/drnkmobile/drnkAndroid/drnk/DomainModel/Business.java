package com.drnkmobile.drnkAndroid.drnk.DomainModel;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by FarisShatat on 10/4/15.
 */
public class Business {


    private final String businessName;

    private Business(String busissName) {
        this.businessName = checkNotNull(busissName);
    }

    public static Business makeWithBusinessName(String name) {
        return new Business(name);
    }

    public static Business makeWithBusinessSpecialName(String specialName){
        return new Business(specialName);
    }

    public static Business makeId(String makeId){
        return new Business(makeId);
    }

    public static Business makeWithAddress(String address){
        return new Business(address);
    }
    public static Business makeWithWeeklySpecials (String week){
        return new Business(week);
    }
    

    public static Business makePhoneNumber(String phoneNumber) {
        return new Business(phoneNumber);
    }

    public static Business makewithBusinessHours(String hours) {
        return new Business(hours);
    }
    public String getBusiness() {
        return businessName;
    }
}



