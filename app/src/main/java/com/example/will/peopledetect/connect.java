package com.example.will.peopledetect;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class connect extends AsyncTask<String, Integer, String> {
    protected String doInBackground(String... urls) {
        URL url;
        HttpURLConnection connection;
        String a ="";
        try{
        url = new URL(urls[0]);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
         BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         String content = "", line;
         while ((line = rd.readLine()) != null) {
             content += line + "\n";
         }
        a = content;
        } catch (MalformedURLException e){

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e){

            e.printStackTrace();

        }

        return a;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        // this is executed on the main thread after the process is over
        // update your UI here

    }
}
