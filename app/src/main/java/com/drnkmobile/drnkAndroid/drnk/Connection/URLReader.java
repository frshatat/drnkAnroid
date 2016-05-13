package com.drnkmobile.drnkAndroid.drnk.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class URLReader {

    HttpURLConnection con;
    public String getJSON(String typeOfBusiness,String currentCity) {

            BufferedReader reader;

            try {
                if(currentCity!=null){
                    currentCity=currentCity.toLowerCase();
                }
                URL url = checkForBusinessType(typeOfBusiness,currentCity);

                con = (HttpURLConnection) url.openConnection();
                StringBuilder stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(con
                        .getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    line += "\n";
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Server Unavailable";
            }

        }

    private URL checkForBusinessType(String typeOfBusiness,String currentCity) throws MalformedURLException {
        URL url;
        if (typeOfBusiness.equals("bars")) {
         url = new URL("http://drnkmedia.com/api/api.php/?company_city=" + currentCity +"&bar=true");
        }
        else if(typeOfBusiness.equals("liquorstores")){
          url = new URL("http://drnkmedia.com/api/api.php/?company_city=" + currentCity + "&liquorstore=true");
        }
        else if (typeOfBusiness.equals("alladdresses")){
            url = new URL("http://drnkmedia.com/api/api.php/?company_city=" + currentCity + "&bar=true&liquorstore=true");
        }
        else{
          url=new URL("http://drnkmedia.com/api/api.php/?company_city=" + currentCity + "&bar=true&liquorstore=true");
        }

        return url;
    }
}
