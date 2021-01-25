package com.wiryaimd.mcconnect;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServlistAdapter extends RecyclerView.Adapter<ServlistAdapter.MyHolder> {

    private ArrayList<DataModel> arrdata;
    private Context context;

    public ServlistAdapter(ArrayList<DataModel> arrdata, Context context){
        this.arrdata = arrdata;
        this.context = context;
    }

    @NonNull
    @Override
    public ServlistAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serverlist, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServlistAdapter.MyHolder holder, final int position) {
        DataModel dataModel = arrdata.get(position);
        String stat = "Java IP: " + dataModel.getIp() + ":" + dataModel.getPort() + "\nMCPE IP: play.serverconvert.my.to (" + dataModel.getMcpeport() + ")\nStatus: Connected";
        holder.serverstat.setText(stat);
        final String mcpeport = dataModel.getMcpeport();
        holder.mycopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String copystr = "play.serverconvert.my.to:" + mcpeport;
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", copystr);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Copied " + copystr, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrdata.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView serverstat, mycopy;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            serverstat = itemView.findViewById(R.id.item_serverstat);
            mycopy = itemView.findViewById(R.id.item_copy);
        }
    }
}
