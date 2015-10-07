package com.drnkmobile.myapplication2.app;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class Special {

    public static Builder withBarName(String barName) {
        return new Builder(barName);
    }


    public static final class Builder {
        private final String barName;
        private final List<BarTableInfo> listOfSpecials = Lists.newArrayList();
        private final List<BarTableInfo> listOfBusinesses = Lists.newArrayList();


        private Builder(String barName) {
            this.barName = checkNotNull(barName);
        }

        public Builder addBusiness(BarTableInfo business) {
            listOfBusinesses.add(business);
            return this;
        }

        public Builder addSpecial(BarTableInfo special) {
            listOfSpecials.add(special);
            return this;
        }

        public Special build() {
            return new Special(this);
        }
    }

    //private final String businessName;
    private final ImmutableList<BarTableInfo> business;
    private final ImmutableList<BarTableInfo> specials;


    public Special(Builder builder) {
       // this.businessName = builder.barName;
        this.business = ImmutableList.copyOf(builder.listOfBusinesses);
        this.specials = ImmutableList.copyOf(builder.listOfSpecials);
    }

//    //public String getBusinessName() {
//        return businessName;
//    }

    public int countBusinesses() {
        return business.size();
    }

    public BarTableInfo getBusinessName(int index) {
        return business.get(index);
    }

    public BarTableInfo getSpecial(int index) {
        return specials.get(index);
    }

    public int countSpecials() {
        return specials.size();
    }

}
