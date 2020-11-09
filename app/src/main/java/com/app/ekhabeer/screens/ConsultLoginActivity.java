package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.ekhabeer.R;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsultLoginActivity extends BaseActivity implements Controller.APICallbackListener {

    Controller mController;
    HashMap<String, String> hashMapMain;
    EditText et_email,et_pass;
    RelativeLayout rl_login;
    RadioButton radioButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_login);

        initView();

    }

    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!et_email.getText().toString().equals("")){

                    if (!et_pass.getText().toString().equals("")){

                        if(!radioButton1.isChecked()){

                            LoginAPI();

                        }else{
                            Toast.makeText(ConsultLoginActivity.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(ConsultLoginActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(ConsultLoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void LoginAPI(){
        hashMapMain.put(Constants.CONSULT_LOGIN , "");
        hashMapMain.put(Constants.EMAIL , et_email.getText().toString());
        hashMapMain.put(Constants.PASSWORD , et_pass.getText().toString());
        mController.LoginConsultantAPI(hashMapMain,ConsultLoginActivity.this);

    }

    @Override
    public void onFetchStart() {

        showLoader(ConsultLoginActivity.this);

        Log.e("ConsultLoginActivity", "onFetchStart>>>: ");
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

        try {
            Log.e("ConsultLoginActivity", "onFetchProgress>>>: " + pojo.getString("id") +">>" + pojo);

            SharedPreferences prefs = this.getSharedPreferences(
                    "Ekhabeer", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_type","consultant");
            editor.putString("email",pojo.getString("email"));
            editor.putString("user_id",pojo.getString("id"));
            editor.putString("fname",pojo.getString("fname"));
            editor.putString("lname",pojo.getString("lname"));
            editor.putString("country",pojo.getString("country"));
            editor.putString("city",pojo.getString("city"));
            editor.putString("address",pojo.getString("address"));
            Log.e("ConsultLoginActivity", "onFetchProgress>>: " + pojo.getString("id"));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {

    }

    @Override
    public void onFetchComplete() {

        dismissLoader();
        Log.e("ConsultLoginActivity", "onFetchComplete>>>: ");
        Intent intent = new Intent(ConsultLoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFetchFailed() {

        dismissLoader();
        AlertDialog("Login error",ConsultLoginActivity.this);
        Log.e("ConsultLoginActivity", "onFetchFailed>>>: ");
    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data) {

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {

    }

}