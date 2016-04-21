import com.drnkmobile.drnkAndroid.drnk.DomainModel.TableInfo;
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

    private final TableInfo businessName = TableInfo.makeWithBusinessName(BUSINESSNAME); //
    private final TableInfo businessSpecialName = TableInfo.makeWithBusinessSpecialName(SPECIAL);
    private final TableInfo businessID = TableInfo.makeId(ID);
    private final TableInfo businessAddress = TableInfo.makeWithAddress(ADDRESS);
    private final TableInfo businessWeeklySpecial = TableInfo.makeWithWeeklySpecials(SPECIAL);


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

