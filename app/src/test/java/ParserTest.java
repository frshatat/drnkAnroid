
import com.drnkmobile.drnkAndroid.drnk.DomainModel.BusinessBuilder;
import com.drnkmobile.drnkAndroid.drnk.DomainModel.Parser;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class ParserTest {
    private BusinessBuilder business;
    private Parser parser;

    //Test Bars JSON

    @Test
    public void testParseExtractsBrothersBarAndGrillTitle() throws JSONException {
        whenParsingBarsJsonFile();

        Assert.assertEquals("Brothers Bar & Grill", business.getBusinessName(1).getBusiness());

    }

    @Test
    public void testParseExtractsBrotherBarGrillAddress() throws JSONException {
        whenParsingBarsJsonFile();

        Assert.assertEquals("1601 W University Ave, Muncie", business.getAddress(1).getBusiness());

    }

    @Test
    public void testParseExtractsBrothersBarGrillID() throws JSONException {
        whenParsingBarsJsonFile();
        Assert.assertEquals("35", business.getId(1).getBusiness());
    }

    //
    @Test
    public void testParseExtractsBrothersBarGrillHours() throws JSONException {
        whenParsingBarsJsonFile();
        Assert.assertEquals("11:00 AM - 3:00 AM", business//
                .getBusinesshours(1).getBusiness());
    }

    @Test
    public void testParseExtractsBrothersBarGrillSpecial() throws JSONException {
        whenParsingBarsJsonFile();
        Assert.assertEquals("$ 1.00 Wells\n" +
                "\n" +
                " $ 2.00 PBR Tall Boys\n" +
                "\n" +
                " $ 6.00 32oz Long Islands", business//
                .getSpecial(1).getBusiness());
    }

    private void whenParsingBarsJsonFile() throws JSONException {
        String inputStream = load("bar.json");

        parser = new Parser(inputStream);
        business = parser.parse("bars");
    }

    //TestLiquorStoreJSON

    @Test
    public void testParseExtractsFriendlyPackageName() throws JSONException {
        whenParsingLiquorStoreJSON();

        Assert.assertEquals("Friendly Package", business.getBusinessName(1).getBusiness());

    }


    @Test
    public void testParseExtractsFriendlyPackageAddress() throws JSONException {
        whenParsingLiquorStoreJSON();

        Assert.assertEquals("1622 W Jackson St, Muncie", business.getAddress(1).getBusiness());

    }

    @Test
    public void testParseExtractsFriendlyPackagelID() throws JSONException {
        whenParsingLiquorStoreJSON();
        Assert.assertEquals("34", business.getId(1).getBusiness());
    }

    //
    @Test
    public void testParseExtractsFriendlyPackageHours() throws JSONException {
        whenParsingLiquorStoreJSON();
        Assert.assertEquals("7:00 AM - 12:30 AM", business//
                .getBusinesshours(1).getBusiness());
    }

    @Test
    public void testParseExtractsFriendlyPackageSpecial() throws JSONException {
        whenParsingLiquorStoreJSON();
        Assert.assertEquals("$ 3.93 Sutter Home Moscato or Pink Moscato 750ml\n" +
                "\n" +
                " $ 6.93 Sam Adams Cold Snap 12-12oz btls\n" +
                "\n" +
                " $ 8.93 Svedka Vodka All Flavors 750ml", business//
                .getSpecial(1).getBusiness());
    }

    private void whenParsingLiquorStoreJSON() throws JSONException {
        String inputStream = load("liquorstore.json");
        parser = new Parser(inputStream);
        business = parser.parse("liquorstores");
    }


    private String load(String jsonFile) {

        InputStream is = getClass().getClassLoader().getResourceAsStream(jsonFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder content = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();

    }
}


