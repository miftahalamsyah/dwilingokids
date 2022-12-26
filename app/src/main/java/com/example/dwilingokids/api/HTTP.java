package com.example.dwilingokids.api;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {
    Context context;
    private String url, method = "GET", data = null, response = null;
    private Integer statusCode = 0;
    private Boolean token = false;
    private LocalStorage localStorage;

    public HTTP(Context context, String url) {
        this.context = context;
        this.url = url;
        localStorage = new LocalStorage(context);
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    public String getResponse() {
        return response;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void send(){
        try {
            URL sUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) sUrl.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
            if (token){
                connection.setRequestProperty("Authorization","Bearer "+localStorage.getToken());
            }
            if (! method.equals("GET")){
                connection.setDoOutput(true);
            }
            if (data != null){
                OutputStream os = connection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();
            }
            statusCode = connection.getResponseCode();

            InputStreamReader isr;
            if (statusCode >= 200 && statusCode <= 299){
                isr = new InputStreamReader(connection.getInputStream());
            }else {
                isr = new InputStreamReader(connection.getErrorStream());
            }

            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            response = sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
