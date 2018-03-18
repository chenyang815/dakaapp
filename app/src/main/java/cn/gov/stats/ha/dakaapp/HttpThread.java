package cn.gov.stats.ha.dakaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sola on 2018/3/18.
 */

public class HttpThread extends Thread {

    private String url;
    private String id;
    private String tag;

    HttpThread(String url, String id, String tag){
        this.id = id;
        this.url = url;
        this.tag = tag;
    }

    private void doPost() {

        try {
            URL httpUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            OutputStream out = conn.getOutputStream();
            String content = id+"&"+tag;
            out.write(content.getBytes());
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str=reader.readLine())!=null){
                sb.append(str);
            }
            System.out.println("result:"+sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doPost();
    }


}
