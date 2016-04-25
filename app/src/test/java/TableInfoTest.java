import com.drnkmobile.drnkAndroid.drnk.DomainModel.Business;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by FarisShatat on 4/17/16.
 */
public class TableInfoTest {
    private final String ADDRESS = "12345 University Ave, Muncie, IN" ;
    private final String ID = "23" ;
    private final String BUSINESSNAME = "The Chug";
    private final String SPECIAL = "Coors Light";

    private final Business businessName = Business.makeWithBusinessName(BUSINESSNAME); //
    private final Business businessSpecialName = Business.makeWithBusinessSpecialName(SPECIAL);
    private final Business businessID = Business.makeId(ID);
    private final Business businessAddress = Business.makeWithAddress(ADDRESS);
    private final Business businessWeeklySpecial = Business.makeWithWeeklySpecials(SPECIAL);


    @Test
    public void testCreate_BusinessName() {
        Assert.assertEquals(BUSINESSNAME, businessName.getBusiness());
    }
    @Test
    public void testCreate_SpecialName() {
        Assert.assertEquals(SPECIAL, businessSpecialName.getBusiness());
    }
    @Test
    public void testCreate_BusinessID() {
        Assert.assertEquals(ID, businessID.getBusiness());
    }
    @Test
    public void testCreate_BusinessAddress() {
        Assert.assertEquals(ADDRESS, businessAddress.getBusiness());
    }
    @Test
    public void testCreate_WeeklySpecials() {
        Assert.assertEquals(SPECIAL, businessWeeklySpecial.getBusiness());
    }
}

