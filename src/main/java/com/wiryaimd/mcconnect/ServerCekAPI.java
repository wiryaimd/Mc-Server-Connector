package com.wiryaimd.mcconnect;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ServerCekAPI {

    private static int status;

    public static int cekServer(final String ip, final String port){
        status = 0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String myurl;
                if (port.equalsIgnoreCase("25565")){
                    myurl = "https://api.mcsrvstat.us/simple/" + ip;
                }else{
                    myurl = "https://api.mcsrvstat.us/simple/" + ip + ":" + port;
                }

                try {
                    URL url = new URL(myurl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    status = connection.getResponseCode();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(status);

        return status;
    }
}
