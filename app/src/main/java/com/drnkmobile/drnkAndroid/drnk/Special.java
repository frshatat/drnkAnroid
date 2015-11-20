package com.drnkmobile.drnkAndroid.drnk;

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
        private final List<BarTableInfo> listOfId = Lists.newArrayList();
        private final List<BarTableInfo> listofAddress = Lists.newArrayList();
        private final List<BarTableInfo> listOfWeekSpecials = Lists.newArrayList();
        private final List<BarTableInfo> listofPhoneNumber=Lists.newArrayList();
        private final List<BarTableInfo> listofBusinessHours = Lists.newArrayList();

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
        public Builder addId(BarTableInfo id) {
            listOfId.add(id);
            return this;
        }
        public Builder addAddress(BarTableInfo address) {
            listofAddress.add(address);
            return this;
        }
        public Builder addWeekSpecials(BarTableInfo week){
            listOfWeekSpecials.add(week);
            return this;
        }

        public Special build() {
            return new Special(this);
        }

        public Builder addPhoneNumber(BarTableInfo businessPhoneNumber) {
            listofPhoneNumber.add(businessPhoneNumber);
            return this;
        }

        public Builder addBusinessHours(BarTableInfo businessHours) {
            listofBusinessHours.add(businessHours);
            return this;
        }
    }

    //private final String businessName;
    private final ImmutableList<BarTableInfo> business;
    private final ImmutableList<BarTableInfo> specials;
    private final ImmutableList<BarTableInfo> id;
    private final ImmutableList<BarTableInfo> address;
    private final ImmutableList<BarTableInfo>weekSpecials;
    private final ImmutableList<BarTableInfo>businessPhoneNumber;
    private final ImmutableList<BarTableInfo>businessHours;
    public Special(Builder builder) {
        // this.businessName = builder.barName;
        this.business = ImmutableList.copyOf(builder.listOfBusinesses);
        this.specials = ImmutableList.copyOf(builder.listOfSpecials);
        this.id = ImmutableList.copyOf(builder.listOfId);
        this.address = ImmutableList.copyOf(builder.listofAddress);
        this.weekSpecials = ImmutableList.copyOf(builder.listOfWeekSpecials);
        this.businessPhoneNumber = ImmutableList.copyOf(builder.listofPhoneNumber);
        this.businessHours = ImmutableList.copyOf(builder.listofBusinessHours);
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
    public BarTableInfo getId(int index){
        return id.get(index);
    }
    public int countId(){
        return id.size();
    }

    public BarTableInfo getAddress(int index){
        return address.get(index);
    }
    public int countAddress(){
        return address.size();
    }

    public BarTableInfo getWeekSpecials(int index){
        return weekSpecials.get(index);
    }
    public int countWeekSpecials(){
        return weekSpecials.size();
    }
    public BarTableInfo getPhoneNumbers(int index){
        return businessPhoneNumber.get(index);
    }
    public int countPhoneNumber(){
        return businessPhoneNumber.size();
    }
    public BarTableInfo getBusinesshours(int index){
        return businessHours.get(index);
    }
    public int countBusinessHours(){
        return businessHours.size();
    }
}
