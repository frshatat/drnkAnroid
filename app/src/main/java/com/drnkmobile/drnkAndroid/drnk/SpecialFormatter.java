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
    private List<String> businessHourList = new ArrayList<>();
    private List<String> businessPhoneNumber = new ArrayList<>();

    public List getBusinessData(Special businessData) {
        if (businessData.countBusinesses() > 0) {
            addElementsToList(businessData);
        } else {
            businessList.add("Unknown Business");
        }
        return businessList;
    }

    private void addElementsToList(Special business) {
        for (int i = 0; i < business.countBusinesses(); i++) {

            specialList.add(formatSpecialName(business.getSpecial(i)));
            businessList.add((formatBusinessName(business.getBusinessName(i))));
        }
    }

    private String formatSpecialName(TableInfo special) {
        return "\n" + special.getTitle().toString();
    }

    public List getInfoForMap(Special special) {
        if (special.countBusinesses() > 0) {
            for (int i = 0; i < special.countBusinesses(); i++) {

                businessList.add((formatBusinessName(special.getBusinessName(i))));
            }
        } else {
            businessList.add("Unknown Business");
        }
        return businessList;
    }

    private String formatBusinessName(TableInfo business) {
        return business.getTitle() + "\n";
    }

    public List getId(Special id) {
        if (id.countId() > 0) {
            addId(id);
        } else {
            businessList.add("0000");
        }
        return businessIdList;
    }

    private void addId(Special id) {
        for (int i = 0; i < id.countId(); i++) {
            businessIdList.add(formatId(id.getId(i)));
        }
    }

    private String formatId(TableInfo id) {
        return id.getTitle();
    }

    public List getBusinessSpecials(Special special) {
        if (special.countSpecials() > 0) {
            addSpecials(special);
            return specialList;
        } else {
            specialList.add("Sorry we couldn't find any specials today.");
        }
        return specialList;
    }

    protected void addSpecials(Special business) {
        for (int i = 0; i < business.countSpecials(); i++) {
            int episodeCount = i + 1;

            specialList.add(formatSpecialName(business.getSpecial(i)));
        }
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

    private void addSpecialsForWeek(Special week) {
        for (int i = 0; i < week.countWeekSpecials(); i++) {
            weekSpecialsList.add(formatSpecialName(week.getWeekSpecials(i)));
        }
    }

    public List getAddress(Special address) {
        if (address.countAddress() > 0) {
            addAddress(address);
        } else {
            addressList.add("Unknown Location");
        }
        return addressList;
    }

    private void addAddress(Special address) {
        for (int i = 0; i < address.countAddress(); i++) {
            addressList.add(formatAddress(address.getAddress(i)));
        }
    }

    private String formatAddress(TableInfo address) {
        return address.getTitle();
    }

    public List getBusinessHours(Special hours) {
        if (hours.countBusinessHours() > 0) {
            addBusinesHours(hours);
        } else {
            businessHourList.add("Business Hours Unavailable");
        }
        return businessHourList;
    }

    private void addPhoneNumber(Special phoneNumber) {
        for (int i = 0; i < phoneNumber.countPhoneNumber(); i++) {
            businessPhoneNumber.add(formatBusinessPhoneNumber(phoneNumber.getPhoneNumbers(i)));
        }
    }

    private String formatBusinessPhoneNumber(TableInfo phoneNumbers) {
        return phoneNumbers.getTitle();
    }

    public List getPhoneNumber(Special phoneNumber) {
        if (phoneNumber.countPhoneNumber() > 0) {
            addPhoneNumber(phoneNumber);
        } else {
            businessPhoneNumber.add("Business Hours Unavailable");
        }
        return businessPhoneNumber;
    }

    private void addBusinesHours(Special hours) {
        for (int i = 0; i < hours.countBusinessHours(); i++) {
            businessHourList.add(formatHours(hours.getBusinesshours(i)));
        }
    }

    private String formatHours(TableInfo businesshours) {
        return businesshours.getTitle();
    }


}
