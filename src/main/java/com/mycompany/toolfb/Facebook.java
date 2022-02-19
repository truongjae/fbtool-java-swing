/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.toolfb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author truon
 */
public class Facebook {
    private String access_token;
    public Facebook(String access_token){
        this.access_token = access_token;
    }
    public void post(String content,int quantity) throws MalformedURLException, IOException {
        String temp = content;
        for(int i =0;i<quantity;i++){
           URL url = new URL("https://graph.facebook.com/v12.0/me/feed");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            String json = "{\"message\":\""+temp+"\",\"access_token\":\""+this.access_token+"\"}";
            byte[] out =  json.getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
            os.write(out);
            }
            temp+=".";
        }
        
    }
}