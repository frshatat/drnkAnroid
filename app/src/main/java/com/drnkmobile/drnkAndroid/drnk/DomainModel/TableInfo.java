package com.drnkmobile.drnkAndroid.drnk.DomainModel;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by FarisShatat on 10/4/15.
 */
public class TableInfo {


    private final String businessName;

    private TableInfo(String busissName) {
        this.businessName = checkNotNull(busissName);
    }

    public static TableInfo makeWithBusinessName(String name) {
        return new TableInfo(name);
    }

    public static TableInfo makeWithBusinessSpecialName(String specialName){
        return new TableInfo(specialName);
    }

    public static TableInfo makeId(String makeId){
        return new TableInfo(makeId);
    }

    public static TableInfo makeWithAddress(String address){
        return new TableInfo(address);
    }
    public static TableInfo makeWithWeeklySpecials (String week){
        return new TableInfo(week);
    }
    

    public static TableInfo makePhoneNumber(String phoneNumber) {
        return new TableInfo(phoneNumber);
    }

    public static TableInfo makewithBusinessHours(String hours) {
        return new TableInfo(hours);
    }
    public String getBusiness() {
        return businessName;
    }
}



