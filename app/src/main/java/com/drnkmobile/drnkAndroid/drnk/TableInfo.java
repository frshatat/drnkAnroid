package com.drnkmobile.drnkAndroid.drnk;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by FarisShatat on 10/4/15.
 */
public class TableInfo {


    private final String title;

    private TableInfo(String title) {
        this.title = checkNotNull(title);
    }

    public static TableInfo makeWithBusinessName(String name) {
        return new TableInfo(name);
    }

    public static TableInfo makeWithBusinessSpecialName(String description){
        return new TableInfo(description);
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
    public String getTitle() {
        return title;
    }

    public static TableInfo makePhoneNumber(String phoneNumber) {
        return new TableInfo(phoneNumber);
    }

    public static TableInfo makewithBusinessHours(String hours) {
        return new TableInfo(hours);
    }
}



