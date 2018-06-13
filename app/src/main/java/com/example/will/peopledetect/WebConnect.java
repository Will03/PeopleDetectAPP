package com.example.will.peopledetect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebConnect extends AsyncTask<String, Void, String> {
    private Context mContext;
    public WebConnect(Context _con){
        mContext=_con;
    }
    /*****************************************************/
    /*  This is a background process for connecting      */
    /*   to the arduino server and sending               */
    /*    the GET request withe the added data           */
    /*****************************************************/
    @Override
    protected String doInBackground(String... params) {
        try {
            /* Change the IP to the IP you set in the arduino sketch */

            URL url = new URL("http://192.168.0.107:5000" + params[0]);
            Log.d("URL",url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            JSONObject json_result = new JSONObject();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Log.d("Response: ", "> " + inputLine);
                result.append(inputLine).append("\n");
            }
//            try {
//                json_result = new JSONObject(result.toString());
//                //Log.d("JSON_result",json_result.toString());
//            }catch(Exception e){
//                Log.e("JSON","FAILED ON CREATE JSON");
//            }
            in.close();
            connection.disconnect();
            Log.d("///////////////",result.toString());
            return result.toString();

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
