package com.wiryaimd.mcgui;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class MyConnect{

    public static FirebaseApp getintialize()throws IOException{
        
        FileInputStream serviceAccount = new FileInputStream("/root/mymcgui/wiryaimd_mcgui/mc-connect-79051-firebase-adminsdk-q9rrd-c51c525e9e.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://mc-connect-79051.firebaseio.com")
            .build();

        FirebaseApp defaultapp = FirebaseApp.initializeApp(options);
        return defaultapp;

    }

}