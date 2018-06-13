package com.example.will.peopledetect;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button seachButton;
    private Button updateB;
    private TextView personDataText;
    private TextView aNumPeoText;
    private TextView bNumPeoText;
    private TextView cNumPeoText;
    private TextView dNumPeoText;

    private EditText searchSectionEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //設定隱藏標題
        getSupportActionBar().hide();

        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        seachButton = (Button)findViewById(R.id.SeachButton);
        personDataText = (TextView)findViewById(R.id.PersonDataText);
        cNumPeoText = (TextView)findViewById(R.id.CNumPeoText);
        aNumPeoText = (TextView)findViewById(R.id.ANumPeoText);
        bNumPeoText = (TextView)findViewById(R.id.BNumPeoText);
        dNumPeoText = (TextView)findViewById(R.id.DNumPeoText);
        updateB = (Button)findViewById(R.id.UpdateB);
        updateB.setOnClickListener(new Button.OnClickListener(){
            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                String test;
                String content = "";
                test = postURL(content,"http://172.20.10.5:5000/showApp");

                String[] split_line = test.split(",");
                int size = split_line.length;
                if(size>=4){
                Log.d("ffff",test);
                aNumPeoText.setText("Section A = "+split_line[0].toString());
                bNumPeoText.setText("Section B = "+split_line[1].toString());
                cNumPeoText.setText("Section C = "+split_line[2].toString());
                dNumPeoText.setText("Section D = "+split_line[3].toString());
                }
            }
        });
        searchSectionEdit = (EditText)findViewById(R.id.SeachSecEdit);
        seachButton.setOnClickListener(new Button.OnClickListener(){
            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
//                WebConnect Webcon = new WebConnect(getApplicationContext());
                String test;
//                test = Webcon.execute("/searchid?id="+"1").toString();
                String content = "id="+searchSectionEdit.getText();
                test = postURL(content,"http://172.20.10.5:5000/searchid");

                personDataText.setText("your section is "+test);
            }
        });

    }

    public static String getUrlContent() {
        try {
            /* Change the IP to the IP you set in the arduino sketch */

            URL url = new URL("http://192.168.0.107:5000/searchid");
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


public String postURL(String content, String address) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
// 设置是否向connection输出，因为这个是post请求，参数要放在
// http正文内，因此需要设为true
            connection.setDoOutput(true);
// Read from the connection. Default is true.
            connection.setDoInput(true);
// 默认是 GET方式
            connection.setRequestMethod("POST");
// Post 请求不能使用缓存
            connection.setUseCaches(false);
//设置本次连接是否自动重定向
            connection.setInstanceFollowRedirects(true);
// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
// 意思是正文是urlencoded编码过的form参数
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
// 要注意的是connection.getOutputStream会隐含的进行connect。
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
            writer.write(content);
//流用完记得关
            out.flush();
            writer.close();
            out.close();
            /* 获取响应 */
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jsonString1 = reader.readLine();
            reader.close();
//该干的都干完了,记得把连接断了
            connection.disconnect();
// 解析 json
            return jsonString1;
        }
        catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

