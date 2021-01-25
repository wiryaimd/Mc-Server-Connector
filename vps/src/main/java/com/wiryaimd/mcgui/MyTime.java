package com.wiryaimd.mcgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.wiryaimd.mcgui.StartGeyser;

public class MyTime {

    private int delay = 70;
    private int period = 1000;
    private int interval = 1000; // 1 minute
    private ArrayList<Integer> arrinterval;
    private ArrayList<DataModel> arrdata;
    private Timer timer;
    private DatabaseReference dbref;
    private int count;

    public ArrayList<Integer> intervalinstance(){
        if(arrinterval != null){
            return arrinterval;
        }else{
            arrinterval = new ArrayList<>();
            return arrinterval;
        }
    }

    public ArrayList<DataModel> datainstance(){
        if(arrdata != null){
            return arrdata;
        }else{
            arrdata = new ArrayList<>();
            return arrdata;
        }
    }

    public void starttime(final StartGeyser startGeyser, DataModel server){
		System.out.println("my startime");
        
        dbref = FirebaseDatabase.getInstance().getReference();
        
		System.out.println("my startime 2");
        arrinterval = intervalinstance();
        arrdata = datainstance();
        arrinterval.add(delay);
        arrdata.add(server);
		
		System.out.println("my startime 3");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                for(int i = 0; i < arrinterval.size(); i++){
                    cekInterval(i, startGeyser, arrdata);
                }
            }
        }, interval, period);

    }

    public void cekInterval(int position, StartGeyser startGeyser, ArrayList<DataModel> server){
        if(arrinterval.get(position) == 1){
            startGeyser.stop(position);
            count = 0;
            dbref.child("serverclose").child(String.valueOf(server.get(position).getNumber())).setValue(server.get(position), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError error, DatabaseReference ref) {

                }
            });
            System.out.println("closed : " + server.get(position).getIp());
            System.out.println("arr size left: " + arrinterval.size());
        }
        if(arrinterval.get(position) > 0){
            int mytime = arrinterval.get(position);
            arrinterval.set(position, --mytime);
            System.out.println("array: " + arrinterval.get(position));
        }
    }

}