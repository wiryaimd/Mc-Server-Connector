package com.wiryaimd.mcconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wiryaimd.mcconnect.fragment.StartFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtip, edtport;
    private ImageView imghelp;
    private Button btncheck;
    private DatabaseReference dbref;
    private int count;
    private RecyclerView recyclerView;

    private TextView announce;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);;

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.main_adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        announce = findViewById(R.id.main_announce);
        edtip = findViewById(R.id.main_ip);
        edtport = findViewById(R.id.main_port);
        imghelp = findViewById(R.id.main_help);
        dbref = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.main_recyclerview);

        imghelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HelpActivity.class));
            }
        });

        overridePendingTransition(R.anim.slide_up, R.anim.slide_out_up);

        dbref.child("info").child("announce").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String msg = snapshot.getValue(String.class);
                announce.setText(msg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbref.child("myserv").child("onlineserver").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataModel> mydatalist = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    DataModel dataModel = data.getValue(DataModel.class);
                    mydatalist.add(dataModel);
                }
                ServlistAdapter adapter = new ServlistAdapter(mydatalist, MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btncheck = findViewById(R.id.main_btncheck);
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragcek = getSupportFragmentManager().findFragmentById(R.id.main_frame);
                if (fragcek == null) {
                    final String ip = edtip.getText().toString();
                    final String port = edtport.getText().toString();

                    char[] myip = ip.toCharArray();
                    boolean cekport = false;
                    boolean cekip = false;

                    if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
                        for (int i = 0; i < myip.length; i++) {
                            if (myip[i] == '.') {
                                cekip = true;
                                break;
                            }
                        }
                        if (port.matches("[0-9]+") && port.length() > 2) {
                            cekport = true;
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Your ip or port is empty", Toast.LENGTH_SHORT).show();
                    }

                    if (cekip && cekport) {
                        int code = ServerCekAPI.cekServer(ip, port);
                        if (code == 200) {
                            count = 0;
                            dbref.child("myserv").child("onlineserver").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    count += 1;
                                    if (count == 1) {
                                        ArrayList<DataModel> mydata = new ArrayList<>();
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            DataModel dataModel = data.getValue(DataModel.class);
                                            mydata.add(dataModel);
                                        }

                                        boolean cek = false;
                                        if (mydata.size() != 0) {
                                            for (int i = 0; i < mydata.size(); i++) {
                                                if (mydata.get(i).getIp().equalsIgnoreCase(ip) &&
                                                        mydata.get(i).getPort().equalsIgnoreCase(port)) {
                                                    cek = true;
                                                    break;
                                                }
                                            }
                                        }

                                        if (!cek) {
                                            Toast.makeText(MainActivity.this, "Server online", Toast.LENGTH_SHORT).show();
                                            StartFragment startFragment = new StartFragment(ip, port, mydata.size(), mydata);
                                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                            ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_out_up);
                                            ft.replace(R.id.main_frame, startFragment).commit();
                                        } else {
                                            MyAlertDialog.showDialog(MainActivity.this, getLayoutInflater(), "The Server Already Converted, \nNOTE: Click 'Check Server' every time you change the ip/port in the text input");
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else if (code == 0) {
                            Toast.makeText(MainActivity.this, "Connection Timeout, please try again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Server is offline", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (!cekip && !cekport) {
                        Toast.makeText(MainActivity.this, "Your ip address and port not valid", Toast.LENGTH_SHORT).show();
                    } else if (!cekip) {
                        Toast.makeText(MainActivity.this, "Your ip address not valid", Toast.LENGTH_SHORT).show();
                    } else if (!cekport) {
                        Toast.makeText(MainActivity.this, "Your port not valid", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    MyAlertDialog.showDialog(MainActivity.this, getLayoutInflater(), "Please close your server before check new server ip");
                }
            }
        });
    }
}
