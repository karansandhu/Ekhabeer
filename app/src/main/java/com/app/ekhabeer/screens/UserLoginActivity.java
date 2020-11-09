package com.app.ekhabeer.screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.ekhabeer.R;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserLoginActivity extends BaseActivity implements Controller.APICallbackListener {

    Controller mController;
    HashMap<String, String> hashMapMain;
    EditText et_email,et_pass;
    RelativeLayout rl_login;
    RadioButton radioButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

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

//                                        if(!radioButton1.isChecked()){

                                              LoginAPI();

//                                        }else{
//                                            Toast.makeText(UserLoginActivity.this, "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
//                                        }

                                    }else{
                                        Toast.makeText(UserLoginActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(UserLoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                                }
            }
        });
    }

    public void LoginAPI(){
        hashMapMain.put(Constants.USER_LOGIN , "");
        hashMapMain.put(Constants.EMAIL , et_email.getText().toString());
        hashMapMain.put(Constants.PASSWORD , et_pass.getText().toString());
        Log.e("checkLoginFlow",">>" + hashMapMain);
        mController.LoginUserAPI(hashMapMain,UserLoginActivity.this);

    }

    @Override
    public void onFetchStart() {
        showLoader(UserLoginActivity.this);
        Log.e("checkLoginAPI", "onFetchStart>>>: ");
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

        try {
            SharedPreferences prefs = this.getSharedPreferences(
                    "Ekhabeer", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_type","user");
            editor.putString("email",pojo.getString("email"));
            editor.putString("user_id",pojo.getString("id"));
            editor.putString("fname",pojo.getString("fname"));
            editor.putString("lname",pojo.getString("lname"));
            editor.putString("contact",pojo.getString("contact"));
            editor.putString("country",pojo.getString("country"));
            editor.putString("city",pojo.getString("city"));
            editor.putString("profile",pojo.getString("profile"));
            editor.putString("address",pojo.getString("address"));
            Log.e("checkUserProfileAPI", "onFetchProgress>>: " + pojo.getString("id"));
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
        Log.e("checkLoginAPI", "onFetchComplete>>>: ");
        Intent intent = new Intent(UserLoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFetchFailed() {
        dismissLoader();
        AlertDialog("Login error",UserLoginActivity.this);
        Log.e("checkLoginAPI", "onFetchFailed>>>: ");
    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data) {

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {

    }

}