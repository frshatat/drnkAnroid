package com.drnkmobile.drnkAndroid.drnk.DomainModel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class BusinessBuilder {

    public static Builder withBusinessName(String barName) {
        return new Builder(barName);
    }


    public static final class Builder {
        private final String barName;
        private final List<Business> listOfSpecials = Lists.newArrayList();
        private final List<Business> listOfBusinesses = Lists.newArrayList();
        private final List<Business> listOfId = Lists.newArrayList();
        private final List<Business> listofAddress = Lists.newArrayList();

        private final List<Business> listofPhoneNumber=Lists.newArrayList();
        private final List<Business> listofBusinessHours = Lists.newArrayList();

        private Builder(String barName) {
            this.barName = checkNotNull(barName);
        }

        public Builder addBusiness(Business business) {
            listOfBusinesses.add(business);
            return this;
        }

        public Builder addSpecial(Business special) {
            listOfSpecials.add(special);
            return this;
        }
        public Builder addId(Business id) {
            listOfId.add(id);
            return this;
        }
        public Builder addAddress(Business address) {
            listofAddress.add(address);
            return this;
        }


        public BusinessBuilder build() {
            return new BusinessBuilder(this);
        }

        public Builder addPhoneNumber(Business businessPhoneNumber) {
            listofPhoneNumber.add(businessPhoneNumber);
            return this;
        }

        public Builder addBusinessHours(Business businessHours) {
            listofBusinessHours.add(businessHours);
            return this;
        }
    }


    private final ImmutableList<Business> business;
    private final ImmutableList<Business> specials;
    private final ImmutableList<Business> id;
    private final ImmutableList<Business> address;

    private final ImmutableList<Business>businessPhoneNumber;
    private final ImmutableList<Business>businessHours;

    public BusinessBuilder(Builder builder) {

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

    public Business getBusinessName(int index) {
        return business.get(index);
    }

    public Business getSpecial(int index) {
        return specials.get(index);
    }

    public int countSpecials() {
        return specials.size();
    }
    public Business getId(int index){
        return id.get(index);
    }
    public int countId(){
        return id.size();
    }

    public Business getAddress(int index){
        return address.get(index);
    }
    public int countAddress(){
        return address.size();
    }

    public Business getPhoneNumbers(int index){
        return businessPhoneNumber.get(index);
    }
    public int countPhoneNumber(){
        return businessPhoneNumber.size();
    }
    public Business getBusinesshours(int index){
        return businessHours.get(index);
    }
    public int countBusinessHours(){
        return businessHours.size();
    }
}
