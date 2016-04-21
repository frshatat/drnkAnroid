import com.drnkmobile.drnkAndroid.drnk.DomainModel.Business;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.TableInfo;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static com.google.common.base.Preconditions.checkArgument;
import static org.junit.Assert.assertEquals;

/**
 * Created by FarisShatat on 4/18/16.
 */
public class BusinessTest {
    private static final String BUSINESSNAME = "My Show";
    private Business business;

    private static final ImmutableList<TableInfo> BUSINESSES = ImmutableList.of(
            TableInfo.makeWithBusinessName("one business"),//
            TableInfo.makeWithBusinessName("two business"));

    private static final ImmutableList<TableInfo> SPECIALS = ImmutableList.of(
            TableInfo.makeWithBusinessSpecialName("one special"),//
            TableInfo.makeWithBusinessSpecialName("two's special"));

    private static final ImmutableList<TableInfo> IDS = ImmutableList.of(
            TableInfo.makeId("ID 1"),//
            TableInfo.makeId("ID 2"));

    private static final ImmutableList<TableInfo> ADDRESSES = ImmutableList.of(
            TableInfo.makeWithAddress("address 1"),//
            TableInfo.makeWithAddress("address 2"));

    private static final ImmutableList<TableInfo> PHONENUMBERS = ImmutableList.of(
            TableInfo.makePhoneNumber("phone number 1"),//
            TableInfo.makePhoneNumber("phone number 2"));

    private static final ImmutableList<TableInfo> HOURS = ImmutableList.of(
            TableInfo.makewithBusinessHours("hour 1"),//
            TableInfo.makewithBusinessHours("hour 2"));
    @Test
    public void testCountEpisodeTitles_addingNone_zero() {
        business = makeBusinessWithoutSpecialOrAddress();
        assertEquals(0, business.countBusinesses());
    }

    private Business makeBusinessWithoutSpecialOrAddress() {
        return Business.withBusinessName(BUSINESSNAME).build();
    }

    @Test
    public void testCountBusinessName_addingOne_one() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(1);
        assertEquals(1, business.countBusinesses());
    }

    @Test
    public void testCountBusinessName_addingTwo_two() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(2);
        assertEquals(2, business.countBusinesses());
    }

    @Test
    public void testGetBusiness_addingOne_retrievesIt() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(1);
        assertEquals(BUSINESSES.get(0), business.getBusinessName(0));
    }

    @Test
    public void testGetBusiness_addingTwo_retrieveSecond() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(2);
        assertEquals(BUSINESSES.get(1), business.getBusinessName(1));
    }

    @Test
    public void testGetSpecialName_addingOne_retrieveFirstDescription() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(1);
        assertEquals(SPECIALS.get(0), business.getSpecial(0));
    }


    @Test
    public void testGetSpecialName_addingTwo_retrieveSecondSpecialName() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(2);
        assertEquals(SPECIALS.get(1), business.getSpecial(1));
    }
    @Test
    public void testGetBusinessID_addingOne_retrieveFirstBusinessID() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(1);
        assertEquals(IDS.get(0), business.getId(0));
    }


    @Test
    public void testGetBusinessID_addingTwo_retrieveSecondBusinessID() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(2);
        assertEquals(IDS.get(1), business.getId(1));
    }
    @Test
    public void testGetBusinessAddress_addingOne_retrieveFirstBusinessAddress() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(1);
        assertEquals(ADDRESSES.get(0), business.getAddress(0));
    }

    @Test
    public void testGetBusinessAddress_addingTwo_retrieveSecondBusinessAddress() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(2);
        assertEquals(ADDRESSES.get(1), business.getAddress(1));
    }
    @Test
    public void testGetBusinessPhoneNumber_addingOne_retrieveFirstBusinessPhoneNumber() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(1);
        assertEquals(PHONENUMBERS.get(0), business.getPhoneNumbers(0));
    }
    @Test
    public void testGetBusinessPhoneNumber_addingTwo_retrieveSecondBusinessPhoneNumber() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(2);
        assertEquals(PHONENUMBERS.get(1), business.getPhoneNumbers(1));
    }

    @Test
    public void testGetBusinessHours_addingOne_retrieveFirstBusinessHours() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(1);
        assertEquals(HOURS.get(0), business.getBusinesshours(0));
    }
    @Test
    public void testGetBusinessHours_addingTwo_retrieveSecondBusinessHours() {
        business = makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(2);
        assertEquals(HOURS.get(1), business.getBusinesshours(1));
    }

    private Business makeBusinesswithSpecial_ID_Address_PhoneNumber_Hours(int count) {
        checkArgument(count >= 0);
        Business.Builder builder = Business//
                .withBusinessName(BUSINESSNAME);
        for (int i = 0; i < count; i++) {
            builder.addBusiness(BUSINESSES.get(i));
            builder.addSpecial(SPECIALS.get(i));
            builder.addId(IDS.get(i));
            builder.addAddress(ADDRESSES.get(i));
            builder.addPhoneNumber(PHONENUMBERS.get(i));
            builder.addBusinessHours(HOURS.get(i));
        }
        return builder.build();
    }

}
