package com.app.ekhabeer.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class ClientRegistrationActivity extends BaseActivity implements Controller.APICallbackListener, AdapterView.OnItemSelectedListener {

    RelativeLayout rl_register;
    Controller mController;
    HashMap<String, String> hashMapMain;
    EditText et_fname,et_mname,et_faname,et_dob,et_age,et_country,et_city,et_address,et_email,et_pass,et_conf_pass,
            et_mobile,et_smobile,et_website;
    Spinner sp_gender;
    String gender;
    String[] country = { "Male", "Female", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration);

        initView();
    }

    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        rl_register = (RelativeLayout) findViewById(R.id.rl_register);
        sp_gender = (Spinner) findViewById(R.id.sp_gender);
        et_fname = (EditText) findViewById(R.id.et_fname);
        et_mname = (EditText) findViewById(R.id.et_mname);
        et_faname = (EditText) findViewById(R.id.et_faname);
        et_dob = (EditText) findViewById(R.id.et_dob);
        et_age = (EditText) findViewById(R.id.et_age);
        et_country = (EditText) findViewById(R.id.et_country);
        et_city = (EditText) findViewById(R.id.et_city);
        et_address = (EditText) findViewById(R.id.et_address);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_conf_pass = (EditText) findViewById(R.id.et_conf_pass);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_smobile = (EditText) findViewById(R.id.et_smobile);
        et_website = (EditText) findViewById(R.id.et_website);

        sp_gender.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_gender.setAdapter(aa);

        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!et_fname.getText().toString().equals("")){

                    if (!et_country.getText().toString().equals("")){

                        if (!et_email.getText().toString().equals("")){

                            if (!et_pass.getText().toString().equals("")){

                                if (!et_conf_pass.getText().toString().equals("")){

                                    if (!et_mobile.getText().toString().equals("")){

                                        if (et_pass.getText().toString().equals(et_conf_pass.getText().toString())){

                                            RegisterAPI();

                                        }else{
                                            Toast.makeText(ClientRegistrationActivity.this, "Your passwords doesn't match!", Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(ClientRegistrationActivity.this, "Please enter Mobile", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(ClientRegistrationActivity.this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(ClientRegistrationActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(ClientRegistrationActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(ClientRegistrationActivity.this, "Please enter Country", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ClientRegistrationActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void RegisterAPI(){
        hashMapMain.put(Constants.FNAME, et_faname.getText().toString());
        hashMapMain.put(Constants.CONSULT_REGISTER , "");
        hashMapMain.put(Constants.MNAME , et_mname.getText().toString());
        hashMapMain.put(Constants.LNAME , et_faname.getText().toString());
        hashMapMain.put(Constants.DOB , et_dob.getText().toString());
        hashMapMain.put(Constants.AGE , et_age.getText().toString());
        hashMapMain.put(Constants.COUNTRY , et_country.getText().toString());
        hashMapMain.put(Constants.GENDER , gender);
        hashMapMain.put(Constants.MOBILE , et_mobile.getText().toString());
        hashMapMain.put(Constants.SMOBILE , et_smobile.getText().toString());
        hashMapMain.put(Constants.EMAIL , et_email.getText().toString());
        hashMapMain.put(Constants.PASSWORD , et_pass.getText().toString());
        hashMapMain.put(Constants.CITY , et_city.getText().toString());
        hashMapMain.put(Constants.ADDRESS , et_address.getText().toString());
        hashMapMain.put(Constants.WEBSITE , et_website.getText().toString());
        hashMapMain.put(Constants.DEGREE , "");
        hashMapMain.put(Constants.SPECIAL , "");
        hashMapMain.put(Constants.UNIVERSITY , "");
        hashMapMain.put(Constants.SUBSPECIAL , "");
        hashMapMain.put(Constants.MEMBERSHIP , "");
        hashMapMain.put(Constants.ACTIVITIES , "");
        hashMapMain.put(Constants.LANGUAGE , "ENG");
        hashMapMain.put(Constants.CATEGORY , "");
        hashMapMain.put(Constants.SUBCATEGORY  , "");
        hashMapMain.put(Constants.ONE_EST_COST  , "");
        hashMapMain.put(Constants.THREE_EST_COST  , "");
        Log.e("checkClientReg",">>" + hashMapMain);
        mController.RegisterClientAPI(hashMapMain,ClientRegistrationActivity.this);

    }

    @Override
    public void onFetchStart() {

        showLoader(ClientRegistrationActivity.this);

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
        Intent intent = new Intent(ClientRegistrationActivity.this,ConsultLoginActivity.class);
        intent.putExtra("user_type","Consultant");
        startActivity(intent);
    }

    @Override
    public void onFetchFailed() {

        dismissLoader();
        AlertDialog("Registration error",ClientRegistrationActivity.this);
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