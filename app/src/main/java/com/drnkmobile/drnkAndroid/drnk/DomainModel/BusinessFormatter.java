package com.drnkmobile.drnkAndroid.drnk.DomainModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class BusinessFormatter {

    List<String> businessList = new ArrayList<String>();
    List<String> specialList = new ArrayList<String>();
    List<String> businessIdList = new ArrayList<String>();
    List<String> addressList = new ArrayList<String>();
    List<String> weekSpecialsList = new ArrayList<String>();
    private List<String> businessHourList = new ArrayList<>();
    private List<String> businessPhoneNumber = new ArrayList<>();

    public List getBusinessData(Business businessData) {
        if (businessData !=null) {
            if (businessData.countBusinesses() > 0) {
                addElementsToList(businessData);
            } else {
                businessList.add("Unknown Business");
            }
        }

        return businessList;
    }

    private void addElementsToList(Business business) {
        for (int i = 0; i < business.countBusinesses(); i++) {


            businessList.add((formatBusinessName(business.getBusinessName(i))));
        }
        for(int i =0;i<business.countSpecials();i++){
            specialList.add(formatSpecialName(business.getSpecial(i)));
        }
    }

    private String formatSpecialName(TableInfo special) {
        return "\n" + special.getBusiness().toString();
    }

    public List getInfoForMap(Business special) {
        if(special != null) {
            if (special.countBusinesses() > 0) {
                for (int i = 0; i < special.countBusinesses(); i++) {

                    businessList.add((formatBusinessName(special.getBusinessName(i))));
                }
            } else {
                businessList.add("Unknown Business");
            }
        }
        return businessList;
    }

    private String formatBusinessName(TableInfo business) {
        return business.getBusiness() + "\n";
    }

    public List getId(Business id) {
        if(id!=null) {
            if (id.countId() > 0) {
                addId(id);
            } else {
                businessIdList.add("0000");
            }
        }
        return businessIdList;
    }

    private void addId(Business id) {
        for (int i = 0; i < id.countId(); i++) {
            businessIdList.add(formatId(id.getId(i)));
        }
    }

    private String formatId(TableInfo id) {
        return id.getBusiness();
    }

    public List getBusinessSpecials(Business special) {
        if(special!=null) {
            if (special.countSpecials() > 0) {
                addSpecials(special);
                return specialList;
            } else {
                specialList.add("Sorry we couldn't find any specials today.");
            }
        }

        return specialList;
    }

    protected void addSpecials(Business business) {
        for (int i = 0; i < business.countSpecials(); i++) {


            specialList.add(formatSpecialName(business.getSpecial(i)));
        }
    }



    public List getAddress(Business address) {
        if(address!=null) {
            if (address.countAddress() > 0) {
                addAddress(address);
            } else {
                addressList.add("Unknown Location");
            }
        }
            return addressList;

    }

    private void addAddress(Business address) {
        for (int i = 0; i < address.countAddress(); i++) {
            addressList.add(formatAddress(address.getAddress(i)));
        }
    }

    private String formatAddress(TableInfo address) {
        return address.getBusiness();
    }

    public List getBusinessHours(Business hours) {
        if(hours!=null) {
            if (hours.countBusinessHours() > 0) {
                addBusinesHours(hours);
            } else {
                businessHourList.add("Business Hours Unavailable");
            }
        }
        return businessHourList;
    }

    private void addPhoneNumber(Business phoneNumber) {
        for (int i = 0; i < phoneNumber.countPhoneNumber(); i++) {
            businessPhoneNumber.add(formatBusinessPhoneNumber(phoneNumber.getPhoneNumbers(i)));
        }
    }

    private String formatBusinessPhoneNumber(TableInfo phoneNumbers) {
        return phoneNumbers.getBusiness();
    }

    public List getPhoneNumber(Business phoneNumber) {
        if(phoneNumber!=null) {
            if (phoneNumber.countPhoneNumber() > 0) {
                addPhoneNumber(phoneNumber);
            } else {
                businessPhoneNumber.add("Business Hours Unavailable");
            }
        }
        return businessPhoneNumber;
    }

    private void addBusinesHours(Business hours) {
        for (int i = 0; i < hours.countBusinessHours(); i++) {
            businessHourList.add(formatHours(hours.getBusinesshours(i)));
        }
    }

    private String formatHours(TableInfo businesshours) {
        return businesshours.getBusiness();
    }


}
