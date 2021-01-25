package com.wiryaimd.mcconnect.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.wiryaimd.mcconnect.DataModel;
import com.wiryaimd.mcconnect.R;

public class OnlineFragment extends Fragment {

    private DataModel dataModel;
    private String mcpeport;

    private TextView serverstat, desk;
    private Button btnclose;

    public OnlineFragment(DataModel dataModel, String mcpeport){
        this.dataModel = dataModel;
        this.mcpeport = mcpeport;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        serverstat = view.findViewById(R.id.online_serverstat);
        desk = view.findViewById(R.id.online_desk);
        btnclose = view.findViewById(R.id.online_close);

        String status = "Server Online! you can join using\nIP: play.serverconvert.my.to\nPort: " + mcpeport;
        serverstat.setText(status);
        serverstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String copystr = "play.serverconvert.my.to:" + mcpeport;
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", copystr);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "Copied " + copystr, Toast.LENGTH_SHORT).show();
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.main_frame));
                ft.commit();
            }
        });

    }
}
