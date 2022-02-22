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
    public Facebook(){
        
    }
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
    
    public void sendMess(String myId,String idUser, String content, String fb_dtsg,String cookie,int quantity) throws MalformedURLException, IOException{
        
        for (int i = 0; i < quantity; i++) {
            URL url = new URL("https://mbasic.facebook.com/messages/send/?icm=1&refid=12");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Cookie", cookie);
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String tids = "cid.c."+myId+":"+idUser;
            String ids = "ids["+idUser+"]";
            String data = "fb_dtsg="+fb_dtsg+
                "&body="+content+
                "&tids="+tids+
                "&"+ids+"="+idUser+
                "&send=Gửi"+
                "&wwwupp=C3"+
                "&referrer="+
                "&ctype="+
                "&cver=legacy";
           byte[] out = data.getBytes(StandardCharsets.UTF_8);
           OutputStream stream = http.getOutputStream();
           stream.write(out);
           http.disconnect();
        }
        
    }
    
    
}