package com.drnkmobile.drnkAndroid.drnk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class SpecialFormatter {

    List<String> businessList = new ArrayList<String>();
    List<String> specialList = new ArrayList<String>();

    public List getBusinessData(Special tvSchedule) {
        if (tvSchedule.countBusinesses() > 0) {
            addElementsToList(tvSchedule);
        } else {
            businessList.add("Unknown Business");
        }
        return businessList;
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


    private void addElementsToList(Special business) {
        for (int i = 0; i < business.countBusinesses(); i++) {
            int episodeCount = i + 1;

            specialList.add(formatSpecialName(business.getSpecial(i)));
            businessList.add((formatBusinessName(business.getBusinessName(i))));


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

    private String formatSpecialName(BarTableInfo special) {
        return "\n" + special.getTitle().toString();
    }
}
