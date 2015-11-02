package com.drnkmobile.drnkAndroid.drnk;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by FarisShatat on 10/4/15.
 */
public class BarTableInfo {


    private final String title;

    private BarTableInfo(String title) {
        this.title = checkNotNull(title);
    }

    public static BarTableInfo makeWithBarName(String name) {
        return new BarTableInfo(name);
    }

    public static BarTableInfo makeWithBarSpecialName(String description){
        return new BarTableInfo(description);
    }

    public static BarTableInfo makeId(String makeId){
        return new BarTableInfo(makeId);
    }

    public static BarTableInfo makeWithAddress(String address){
        return new BarTableInfo(address);
    }
    public static BarTableInfo makeWithWeeklySpecials (String week){
        return new BarTableInfo(week);
    }
    public String getTitle() {
        return title;
    }
}



