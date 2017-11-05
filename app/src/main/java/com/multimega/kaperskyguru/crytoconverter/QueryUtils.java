package com.multimega.kaperskyguru.crytoconverter;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.multimega.kaperskyguru.crytoconverter.MainActivity.LOG_TAG;

public final class QueryUtils {

    private QueryUtils() {
    }


    public static List<allCurrencies> makeHTTPRequestAndFetchCurrencyData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object and
        // Return the {@link Event}
        return extractCurrencies(jsonResponse);
    }

    public static List<allCurrencies> extractCurrencies(String JSONOfCurrencies) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONOfCurrencies)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding devs to
        List<allCurrencies> currencies = new ArrayList<>();

        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // build up a list of Developers objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(JSONOfCurrencies);

            JSONObject btcObject = baseJsonResponse.getJSONObject("BTC");
            JSONObject ethObj = baseJsonResponse.getJSONObject("ETH");

            Log.e("btc", String.valueOf(btcObject));


            Iterator<String> iterator = btcObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                double btcValue = btcObject.getDouble(key);
                double etherumValue = ethObj.getDouble(key);

                Log.e("key", String.valueOf(key));
                Log.e("value", String.valueOf(btcValue));

                allCurrencies curr = new allCurrencies(key, btcValue, etherumValue);

                // Add the new {@link Developers} to the list of devs.
                currencies.add(curr);

            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the currencies JSON results", e);
        }

        // Return the list of currencies
        return currencies;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}

