package com.wiryaimd.mcgui;

import java.io.IOException;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wiryaimd.mcgui.gui.MainGui;

public class Main {

    public static DatabaseReference dbref;
    public static StartGeyser startGeyser;
    public static MyTime myTime;

    static int masuk, masuk2;
    private static boolean skiped = false;

    public static void main(String[] args) throws IOException{

        MainGui maingui = new MainGui();
        maingui.setVisible(true);

        dbref = FirebaseDatabase.getInstance(MyConnect.getintialize()).getReference();

        startGeyser = new StartGeyser();
        myTime = new MyTime();

        masuk = 0;
        dbref.child("myserv").child("onlineserver").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                ArrayList<DataModel> arrdata = new ArrayList<>();
                System.out.println("masuk mamang");

                for(DataSnapshot data : snapshot.getChildren()){
                    DataModel dataModel = data.getValue(DataModel.class);
                    arrdata.add(dataModel);
                }


                System.out.println("size main: " + arrdata.size());

                if(!skiped) {
                    try {
                        DataModel servernew = arrdata.get(arrdata.size() - 1);
                        startGeyser.start(servernew.getIp(), servernew.getPort(), arrdata.size(), servernew.getMcpeport());
                        myTime.starttime(startGeyser, servernew);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        masuk2 = 0;
        dbref.child("serverclose").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                masuk2 += 1;
                if (masuk2 != 1) {
                    System.out.println("closing..");
                    skiped = true;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DataModel closedata = data.getValue(DataModel.class);
                        dbref.child("myserv").child("onlineserver").child(String.valueOf(closedata.getNumber())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError error, DatabaseReference ref) {
                            }
                        });
                        dbref.child("serverclose").child(String.valueOf(closedata.getNumber())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError error, DatabaseReference ref) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}