package com.wiryaimd.mcconnect;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyAlertDialog {

    public static void showDialog(Context context, LayoutInflater inflater, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogview = inflater.inflate(R.layout.dialog_alertlogin, null, false);
        builder.setView(dialogview);

        Button btnpostive = dialogview.findViewById(R.id.dialog_positive);
        TextView desk = dialogview.findViewById(R.id.dialog_desk);
        desk.setText(msg);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnpostive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
