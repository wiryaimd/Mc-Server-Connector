package com.wiryaimd.mcconnect.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wiryaimd.mcconnect.DataModel;
import com.wiryaimd.mcconnect.MyAlertDialog;
import com.wiryaimd.mcconnect.R;

import java.util.ArrayList;

public class StartFragment extends Fragment {

    private DatabaseReference dbref;
    private String ip, port;
    private Button btnstart;
    private int cek, onlinecount;
    private ArrayList<DataModel> myarrdata;

    public StartFragment(String ip, String port, int onlinecount, ArrayList<DataModel> myarrdata){
        this.ip = ip;
        this.port = port;
        this.onlinecount = onlinecount;
        this.myarrdata = myarrdata;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dbref = FirebaseDatabase.getInstance().getReference();

        btnstart = view.findViewById(R.id.start_btn);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cek = 0;
                final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Starting the server..", "Connecting to RDP Server, please wait..", true);
                dbref.child("myserv").child("historyserver").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cek += 1;
                        if (cek == 1) {
                            int datacount = 0;
                            for (DataSnapshot data : snapshot.getChildren()) {
                                datacount += 1;
                            }
                            datacount += 1;

                            String[] listport = {"19132", "19133", "19134", "19135", "19136", "19137", "19138", "19139", "19140", "19141", "19142", "19143", "19144", "19145", "19146", "19147", "19148", "19149", "19150", "19151", "19152", "19153", "19154", "19155", "19156", "19157", "19158", "19159", "19160", };
                            String mcpeport = "19132";
                            boolean cekport = false;
                            if(myarrdata.size() != 0) {
                                mainlist: for (int i = 0; i < listport.length; i++) {
                                    ceklist: for (int j = 0; j < myarrdata.size(); j++) {
                                        if (listport[i].equalsIgnoreCase(myarrdata.get(j).getMcpeport())) {
                                            cekport = false;
                                            break ceklist;
                                        } else {
                                            cekport = true;
                                        }
                                    }
                                    if (cekport) {
                                        mcpeport = listport[i];
                                        break mainlist;
                                    }
                                }
                            }else{
                                cekport = true;
                            }

                            if(cekport) {
                                DataModel dataModel = new DataModel(ip, port, datacount, mcpeport);
                                dbref.child("myserv").child("onlineserver").child(String.valueOf(datacount)).setValue(dataModel);
                                dbref.child("myserv").child("historyserver").child(String.valueOf(datacount)).setValue(dataModel);
                                progressDialog.dismiss();
                                MyAlertDialog.showDialog(getContext(), getLayoutInflater(), "The server successfuly online!");
                                OnlineFragment onlineFragment = new OnlineFragment(dataModel, dataModel.getMcpeport());
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.setCustomAnimations(R.anim.slide_up, R.anim.slide_out_up);
                                ft.replace(R.id.main_frame, onlineFragment).commit();
                            }else{
                                progressDialog.dismiss();
                                MyAlertDialog.showDialog(getContext(), getLayoutInflater(), "Sorry, the online server in this app has reached the limit, please wait for some servers to go offline");
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
