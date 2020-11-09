package com.app.ekhabeer.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.ekhabeer.R;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRegistrationActivity extends BaseActivity implements Controller.APICallbackListener, AdapterView.OnItemSelectedListener {

    RelativeLayout rl_register;
    Controller mController;
    HashMap<String, String> hashMapMain;
    EditText et_fname,et_lname,et_email,et_phone,et_address,et_country,et_city,et_zipcode,et_pass,et_conf_pass;
    Spinner sp_gender;
    String gender;
    String[] country = { "Male", "Female", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        initView();
    }

    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        rl_register = (RelativeLayout) findViewById(R.id.rl_register);
        sp_gender = (Spinner) findViewById(R.id.sp_gender);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_lname = (EditText) findViewById(R.id.et_lname);
        et_zipcode = (EditText) findViewById(R.id.et_zipcode);
        et_fname = (EditText) findViewById(R.id.et_fname);
        et_country = (EditText) findViewById(R.id.et_country);
        et_city = (EditText) findViewById(R.id.et_city);
        et_address = (EditText) findViewById(R.id.et_address);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_conf_pass = (EditText) findViewById(R.id.et_conf_pass);

        rl_register = (RelativeLayout) findViewById(R.id.rl_register);
        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_fname.getText().toString().equals("")){

                    if (!et_country.getText().toString().equals("")){

                        if (!et_email.getText().toString().equals("")){

                            if (!et_pass.getText().toString().equals("")){

                                if (!et_conf_pass.getText().toString().equals("")){

                                    if (!et_phone.getText().toString().equals("")){

                                        if (et_pass.getText().toString().equals(et_conf_pass.getText().toString())){

                                            RegisterAPI();

                                        }else{
                                            Toast.makeText(UserRegistrationActivity.this, "Your passwords doesn't match!", Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(UserRegistrationActivity.this, "Please enter Mobile", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(UserRegistrationActivity.this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(UserRegistrationActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(UserRegistrationActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(UserRegistrationActivity.this, "Please enter Country", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(UserRegistrationActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void RegisterAPI(){
        hashMapMain.put(Constants.FNAME, et_fname.getText().toString());
        hashMapMain.put(Constants.LNAME , et_lname.getText().toString());
        hashMapMain.put(Constants.EMAIL , et_email.getText().toString());
        hashMapMain.put(Constants.CONTACT , et_phone.getText().toString());
        hashMapMain.put(Constants.USER_REGISTER , "");
        hashMapMain.put(Constants.ADDRESS , et_address.getText().toString());
        hashMapMain.put(Constants.COUNTRY , et_country.getText().toString());
        hashMapMain.put(Constants.ZIPCODE , et_zipcode.getText().toString());
        hashMapMain.put(Constants.PASSWORD , et_pass.getText().toString());
        hashMapMain.put(Constants.CITY , et_city.getText().toString());
        hashMapMain.put(Constants.PROFILE , "111");
        Log.e("checkUserReg",">>" + hashMapMain);
        mController.RegisterUserAPI(hashMapMain,UserRegistrationActivity.this);

    }

    @Override
    public void onFetchStart() {

        showLoader(UserRegistrationActivity.this);

        Log.e("checkRegisterAPI", "onFetchStart>>>: ");
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

        Log.e("checkRegisterAPI", "onFetchProgress>>>: " + pojo);
    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {

    }

    @Override
    public void onFetchComplete() {

        dismissLoader();
        Log.e("checkRegisterAPI", "onFetchComplete>>>: ");
        Intent intent = new Intent(UserRegistrationActivity.this,UserLoginActivity.class);
        intent.putExtra("user_type","User");
        startActivity(intent);
    }

    @Override
    public void onFetchFailed() {

        dismissLoader();
        AlertDialog("Registration error",UserRegistrationActivity.this);
        Log.e("checkRegisterAPI", "onFetchFailed>>>: ");
    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data) {

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        gender = country[i];
        Toast.makeText(getApplicationContext(),country[i] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}