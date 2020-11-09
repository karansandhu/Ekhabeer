package com.app.ekhabeer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.ekhabeer.R;
import com.app.ekhabeer.screens.UserLoginActivity;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class BaseActivity extends AppCompatActivity {

    ACProgressFlower dialog;

    public void showLoader(Activity activity){
        dialog = new ACProgressFlower.Builder(activity)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(activity.getResources().getColor(R.color.colorAccent))
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
    }

    public void dismissLoader(){
        dialog.dismiss();
    }

    public void AlertDialog(String message, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( dialog!=null && dialog.isShowing() ){
            dialog.cancel();
        }
    }

}
