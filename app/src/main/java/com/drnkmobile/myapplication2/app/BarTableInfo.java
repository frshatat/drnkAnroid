package com.drnkmobile.myapplication2.app;

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

    public static BarTableInfo makeServiceId(String ServiceId){
        return new BarTableInfo(ServiceId);
    }

    public static BarTableInfo makeServiceName(String serviceName){
        return new BarTableInfo(serviceName);
    }

    public String getTitle() {
        return title;
    }
}

//    public ArrayList<BarTableInfo> barTableInfo = new ArrayList<BarTableInfo>();
//    public String name = "Brother's Bar and Grill";
//    public String address = "Adress of Brother's Bar and Grill, Muncie, IN";
//    public String barImage = "";
//    public String special1 = "Special 1";
//    public String special2 = "Special 2";
//    public String special3 = "Special 3";
//    public String businessId = "";
//
//    public BarTableInfo(String id,String name , String address, String special1,String special2, String special3){
//
//        this.name = name;
//        this.address = address;
//       // this.barImage = generateImage(id);
//        this.special1 = special1;
//        this.special2 = special2;
//        this.special3 = special3;
//
//    }

