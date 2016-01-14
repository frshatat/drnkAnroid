package com.drnkmobile.drnkAndroid.drnk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by FarisShatat on 10/4/15.
 */
public class URLReader {

    HttpURLConnection con;
    public String getJSON(String typeOfBusiness) {

            BufferedReader reader;

            try {
                URL url = new URL("http://drnkmobile.com/api/v1/businesses/"+typeOfBusiness+"/?zipcode=47303&radius=10");
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
                return null;
            }
            finally {
                con.disconnect();
            }
        }
    }
