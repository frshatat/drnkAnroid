package com.drnkmobile.drnkAndroid.drnk.DomainModel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class Business {

    public static Builder withBusinessName(String barName) {
        return new Builder(barName);
    }


    public static final class Builder {
        private final String barName;
        private final List<TableInfo> listOfSpecials = Lists.newArrayList();
        private final List<TableInfo> listOfBusinesses = Lists.newArrayList();
        private final List<TableInfo> listOfId = Lists.newArrayList();
        private final List<TableInfo> listofAddress = Lists.newArrayList();

        private final List<TableInfo> listofPhoneNumber=Lists.newArrayList();
        private final List<TableInfo> listofBusinessHours = Lists.newArrayList();

        private Builder(String barName) {
            this.barName = checkNotNull(barName);
        }

        public Builder addBusiness(TableInfo business) {
            listOfBusinesses.add(business);
            return this;
        }

        public Builder addSpecial(TableInfo special) {
            listOfSpecials.add(special);
            return this;
        }
        public Builder addId(TableInfo id) {
            listOfId.add(id);
            return this;
        }
        public Builder addAddress(TableInfo address) {
            listofAddress.add(address);
            return this;
        }


        public Business build() {
            return new Business(this);
        }

        public Builder addPhoneNumber(TableInfo businessPhoneNumber) {
            listofPhoneNumber.add(businessPhoneNumber);
            return this;
        }

        public Builder addBusinessHours(TableInfo businessHours) {
            listofBusinessHours.add(businessHours);
            return this;
        }
    }


    private final ImmutableList<TableInfo> business;
    private final ImmutableList<TableInfo> specials;
    private final ImmutableList<TableInfo> id;
    private final ImmutableList<TableInfo> address;

    private final ImmutableList<TableInfo>businessPhoneNumber;
    private final ImmutableList<TableInfo>businessHours;
    public Business(Builder builder) {

        this.business = ImmutableList.copyOf(builder.listOfBusinesses);
        this.specials = ImmutableList.copyOf(builder.listOfSpecials);
        this.id = ImmutableList.copyOf(builder.listOfId);
        this.address = ImmutableList.copyOf(builder.listofAddress);
        this.businessPhoneNumber = ImmutableList.copyOf(builder.listofPhoneNumber);
        this.businessHours = ImmutableList.copyOf(builder.listofBusinessHours);
    }



    public int countBusinesses() {
        return business.size();
    }

    public TableInfo getBusinessName(int index) {
        return business.get(index);
    }

    public TableInfo getSpecial(int index) {
        return specials.get(index);
    }

    public int countSpecials() {
        return specials.size();
    }
    public TableInfo getId(int index){
        return id.get(index);
    }
    public int countId(){
        return id.size();
    }

    public TableInfo getAddress(int index){
        return address.get(index);
    }
    public int countAddress(){
        return address.size();
    }

    public TableInfo getPhoneNumbers(int index){
        return businessPhoneNumber.get(index);
    }
    public int countPhoneNumber(){
        return businessPhoneNumber.size();
    }
    public TableInfo getBusinesshours(int index){
        return businessHours.get(index);
    }
    public int countBusinessHours(){
        return businessHours.size();
    }
}
