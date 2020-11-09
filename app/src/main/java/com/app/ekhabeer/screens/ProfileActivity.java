package com.app.ekhabeer.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ekhabeer.R;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;
import com.app.ekhabeer.utils.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends BaseActivity implements Controller.APICallbackListener, AdapterView.OnItemSelectedListener  {

    String user_type,user_id,availabilty;
    Controller mController;
    HashMap<String, String> hashMapMain;
    ImageView iv_profile_user;
    RelativeLayout rl_save;
    private final int PICK_IMAGE_REQUEST = 22;
    ImageView iv_back;
    TextView tv_toolbar_title;
    private Uri filePath;
    SharedPreferences prefs;
    Spinner sp_avail,sp_gender;
    String[] avail = { "Available", "Busy"};
    EditText et_profile_fname,et_profile_lname,et_profile_dob,et_profile_no,et_profile_email,et_profile_city,et_profile_address,et_profile_country;
    EditText et_profile_mname,et_profile_age,et_profile_nation,et_profile_sno,et_profile_web,et_profile_qua,et_profile_degree,
            et_profile_spec,et_profile_cer,et_profile_mem,et_profile_act,et_profile_cat,et_profile_res,et_profile_est,
            et_profile_three_cost,et_profile_four_cost,et_profile_six_cost,et_profile_one_mon,et_profile_three_mon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        hashMapMain = new HashMap<>();
        if (mController == null) {
            mController = new Controller(this);
        }

        user_type = intent.getStringExtra("user_type");
//        user_type = "consultant";
        if (user_type.equals("user")){
            setContentView(R.layout.activity_user_profile);
            InitUserView();
        }else{
            setContentView(R.layout.activity_consul_profile);
            InitConsulView();
        }


    }

    public void InitUserView(){

        prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
        rl_save = (RelativeLayout) findViewById(R.id.rl_save);
        iv_profile_user = (ImageView) findViewById(R.id.iv_profile_user);
        et_profile_fname = (EditText) findViewById(R.id.et_profile_fname);
        et_profile_lname = (EditText) findViewById(R.id.et_profile_lname);
        et_profile_country = (EditText) findViewById(R.id.et_profile_country);
        et_profile_dob = (EditText) findViewById(R.id.et_profile_dob);
        et_profile_no = (EditText) findViewById(R.id.et_profile_no);
        et_profile_email = (EditText) findViewById(R.id.et_profile_email);
        et_profile_city = (EditText) findViewById(R.id.et_profile_city);
        et_profile_address = (EditText) findViewById(R.id.et_profile_address);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_toolbar_title.setText(R.string.profile);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserProfile();
            }
        });
        iv_profile_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
        UserProfileAPI();
    }

    public void InitConsulView(){

        prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
        rl_save = (RelativeLayout) findViewById(R.id.rl_save);
        iv_profile_user = (ImageView) findViewById(R.id.iv_profile_user);
        et_profile_fname = (EditText) findViewById(R.id.et_profile_fname);
        et_profile_mname = (EditText) findViewById(R.id.et_profile_mname);
        et_profile_lname = (EditText) findViewById(R.id.et_profile_lname);
        et_profile_dob = (EditText) findViewById(R.id.et_profile_dob);
        et_profile_age = (EditText) findViewById(R.id.et_profile_age);
        sp_avail = (Spinner) findViewById(R.id.sp_avail);
        et_profile_nation = (EditText) findViewById(R.id.et_profile_nation);
        et_profile_country = (EditText) findViewById(R.id.et_profile_country);
        et_profile_no = (EditText) findViewById(R.id.et_profile_no);
        et_profile_sno = (EditText) findViewById(R.id.et_profile_sno);
        et_profile_email = (EditText) findViewById(R.id.et_profile_email);
        et_profile_city = (EditText) findViewById(R.id.et_profile_city);
        et_profile_address = (EditText) findViewById(R.id.et_profile_address);
        et_profile_web = (EditText) findViewById(R.id.et_profile_web);
        et_profile_qua = (EditText) findViewById(R.id.et_profile_qua);
        et_profile_degree = (EditText) findViewById(R.id.et_profile_degree);
        et_profile_spec = (EditText) findViewById(R.id.et_profile_spec);
        et_profile_cer = (EditText) findViewById(R.id.et_profile_cer);
        et_profile_mem = (EditText) findViewById(R.id.et_profile_mem);
        et_profile_act = (EditText) findViewById(R.id.et_profile_act);
        et_profile_cat = (EditText) findViewById(R.id.et_profile_cat);
        et_profile_res = (EditText) findViewById(R.id.et_profile_res);
        et_profile_est = (EditText) findViewById(R.id.et_profile_est);
        et_profile_three_cost = (EditText) findViewById(R.id.et_profile_three_cost);
        et_profile_four_cost = (EditText) findViewById(R.id.et_profile_four_cost);
        et_profile_six_cost = (EditText) findViewById(R.id.et_profile_six_cost);
        et_profile_one_mon = (EditText) findViewById(R.id.et_profile_one_mon);
        et_profile_three_mon = (EditText) findViewById(R.id.et_profile_three_mon);
        et_profile_res = (EditText) findViewById(R.id.et_profile_res);
        et_profile_res = (EditText) findViewById(R.id.et_profile_res);

        sp_avail.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,avail);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp_avail.setAdapter(aa);

        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateConsulProfile();
            }
        });
        iv_profile_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
        UserProfileAPI();
    }

    public void UpdateConsulProfile(){
        hashMapMain.clear();

        hashMapMain.put(Constants.UPDATE_PROFILE , "");
        hashMapMain.put(Constants.TYPE , "consultant");
        hashMapMain.put(Constants.ID , prefs.getString("user_id",""));
//        hashMapMain.put(Constants.ID , "1");
        hashMapMain.put(Constants.PROFILE , String.valueOf(filePath));
        hashMapMain.put(Constants.FNAME , et_profile_fname.getText().toString());
        hashMapMain.put(Constants.MNAME , et_profile_mname.getText().toString());
        hashMapMain.put(Constants.LNAME , et_profile_lname.getText().toString());
        hashMapMain.put(Constants.DOB , et_profile_dob.getText().toString());
        hashMapMain.put(Constants.AGE , et_profile_age.getText().toString());
        hashMapMain.put(Constants.COUNTRY , et_profile_country.getText().toString());
        hashMapMain.put(Constants.GENDER , "");
        hashMapMain.put(Constants.MOBILE , et_profile_no.getText().toString());
        hashMapMain.put(Constants.SMOBILE , et_profile_sno.getText().toString());
        hashMapMain.put(Constants.EMAIL , et_profile_email.getText().toString());
        hashMapMain.put(Constants.CITY , et_profile_city.getText().toString());
        hashMapMain.put(Constants.ADDRESS , et_profile_address.getText().toString());
        hashMapMain.put(Constants.WEBSITE , et_profile_web.getText().toString());
        hashMapMain.put(Constants.DEGREE , et_profile_degree.getText().toString());
        hashMapMain.put(Constants.SPECIAL , et_profile_spec.getText().toString());
        hashMapMain.put(Constants.UNIVERSITY , "");
        hashMapMain.put(Constants.SUBSPECIAL , "");
        hashMapMain.put(Constants.MEMBERSHIP , et_profile_mem.getText().toString());
        hashMapMain.put(Constants.ACTIVITIES , et_profile_act.getText().toString());
        hashMapMain.put(Constants.LANGUAGE , "English");
        hashMapMain.put(Constants.CATEGORY , et_profile_cat.getText().toString());
        hashMapMain.put(Constants.SUBCATEGORY , "");
        hashMapMain.put(Constants.ONE_EST_COST , et_profile_one_mon.getText().toString());
        hashMapMain.put(Constants.THREE_EST_COST , et_profile_three_cost.getText().toString());
        Log.e("checkUpdateFlow","<<UpdateUserProfile>>" + hashMapMain);
        mController.UpdateConsulProfile(hashMapMain,ProfileActivity.this);

    }

    public void UserProfileAPI(){

        hashMapMain.put(Constants.GET_PROFILE_INFO , "");
        hashMapMain.put(Constants.TYPE , user_type);
        hashMapMain.put(Constants.ID , prefs.getString("user_id",""));
        Log.e("checkUserProfileAPI",">>" + hashMapMain);
        mController.UserProfileAPI(hashMapMain,ProfileActivity.this);

    }

    public void UpdateUserProfile(){
        hashMapMain.clear();
        hashMapMain.put(Constants.UPDATE_PROFILE , "");
        hashMapMain.put(Constants.TYPE , "user");
        hashMapMain.put(Constants.ID , prefs.getString("user_id",""));
//        hashMapMain.put(Constants.ID , "11");
        hashMapMain.put(Constants.PROFILE , String.valueOf(filePath));
        hashMapMain.put(Constants.FNAME , et_profile_fname.getText().toString());
        hashMapMain.put(Constants.LNAME , et_profile_lname.getText().toString());
        hashMapMain.put(Constants.EMAIL , et_profile_email.getText().toString());
        hashMapMain.put(Constants.CONTACT , et_profile_no.getText().toString());
        hashMapMain.put(Constants.ADDRESS , et_profile_address.getText().toString());
        hashMapMain.put(Constants.COUNTRY , et_profile_country.getText().toString());
        hashMapMain.put(Constants.CITY , et_profile_city.getText().toString());
        hashMapMain.put(Constants.ZIPCODE , "");
        Log.e("checkUpdateFlow","<<UpdateUserProfile>>" + hashMapMain);
        mController.UpdateUserProfile(hashMapMain,ProfileActivity.this);

    }

    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);

                iv_profile_user.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFetchStart() {
        showLoader(ProfileActivity.this);

    }

    @Override
    public void onFetchProgress(JSONObject pojo) {
        try {
            Log.e("checkProfilePAge","0>>" + pojo);
            if (user_type.equals("user")){
                Log.e("checkProfilePAge","1>>" + pojo.getString(Constants.EMAIL));
                et_profile_fname.setText(pojo.getString(Constants.FNAME));
                et_profile_lname.setText(pojo.getString(Constants.LNAME));
//                et_profile_dob.setText(pojo.getString(Constants.DOB));
                et_profile_no.setText(pojo.getString(Constants.CONTACT));
                et_profile_email.setText(pojo.getString(Constants.EMAIL));
                et_profile_city.setText(pojo.getString(Constants.CITY));
                et_profile_country.setText(pojo.getString(Constants.COUNTRY));
                et_profile_address.setText(pojo.getString(Constants.ADDRESS));

                try {
                    SharedPreferences prefs = this.getSharedPreferences(
                            "Ekhabeer", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user_type","user");
                    editor.putString("email",pojo.getString(Constants.EMAIL));
                    editor.putString("user_id",pojo.getString(Constants.ID));
                    editor.putString("fname",pojo.getString(Constants.FNAME));
                    editor.putString("lname",pojo.getString(Constants.LNAME));
                    editor.putString("contact",pojo.getString(Constants.CONTACT));
                    editor.putString("country",pojo.getString(Constants.COUNTRY));
                    editor.putString("city",pojo.getString(Constants.CITY));
                    editor.putString("profile",pojo.getString(Constants.PROFILE));
                    editor.putString("address",pojo.getString(Constants.ADDRESS));
                    Log.e("checkUserProfileAPI", "onFetchProgress>>: " + pojo.getString("id"));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Log.e("checkProfilePAge","2>>" + pojo.getString(Constants.EMAIL));
                et_profile_fname.setText(pojo.getString(Constants.FNAME));
                et_profile_mname.setText(pojo.getString(Constants.MNAME));
                et_profile_lname.setText(pojo.getString(Constants.LNAME));
//                et_profile_dob.setText(pojo.getString(Constants.DOB));
                et_profile_age.setText(pojo.getString(Constants.AGE));
//                et_profile_nation.setText(pojo.getString(Constants.NA));
                et_profile_no.setText(pojo.getString(Constants.MOBILE));
                et_profile_country.setText(pojo.getString(Constants.COUNTRY));
                et_profile_sno.setText(pojo.getString(Constants.SMOBILE));
                et_profile_email.setText(pojo.getString(Constants.EMAIL));
                et_profile_city.setText(pojo.getString(Constants.CITY));
                et_profile_address.setText(pojo.getString(Constants.ADDRESS));
                et_profile_web.setText(pojo.getString(Constants.WEBSITE));
//                et_profile_qua.setText(pojo.getString(Constants.Q));
                et_profile_degree.setText(pojo.getString(Constants.DEGREE));
                et_profile_spec.setText(pojo.getString(Constants.SPECIAL));
//                et_profile_cer.setText(pojo.getString(Constants.CER));
                et_profile_mem.setText(pojo.getString(Constants.MEMBERSHIP));
                et_profile_act.setText(pojo.getString(Constants.ACTIVITIES));
                et_profile_cat.setText(pojo.getString(Constants.CATEGORY));
                et_profile_res.setText(pojo.getString(Constants.RESPONSE_TIME));
//                et_profile_est.setText(pojo.getString(Constants.ES));
                et_profile_three_cost.setText(pojo.getString(Constants.THIRTY_EST_COST));
                et_profile_four_cost.setText(pojo.getString(Constants.FORTYFIVE_EST_COST));
                et_profile_six_cost.setText(pojo.getString(Constants.SIXTY_EST_COST));
                et_profile_one_mon.setText(pojo.getString(Constants.ONE_EST_COST));
                et_profile_three_mon.setText(pojo.getString(Constants.THREE_EST_COST));

                try {
                    Log.e("ConsultLoginActivity", "onFetchProgress>>>: " + pojo.getString("id") +">>" + pojo);

                    SharedPreferences prefs = this.getSharedPreferences(
                            "Ekhabeer", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user_type","consultant");
                    editor.putString("email",pojo.getString(Constants.EMAIL));
                    editor.putString("user_id",pojo.getString(Constants.ID));
                    editor.putString("fname",pojo.getString(Constants.FNAME));
                    editor.putString("lname",pojo.getString(Constants.LNAME));
                    editor.putString("country",pojo.getString(Constants.COUNTRY));
                    editor.putString("city",pojo.getString(Constants.CITY));
                    editor.putString("address",pojo.getString(Constants.ADDRESS));
                    editor.putString("profile",pojo.getString(Constants.PROFILE));
                    Log.e("ConsultLoginActivity", "onFetchProgress>>: " + pojo.getString("id"));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e){

        }
    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {

    }

    @Override
    public void onFetchComplete() {
        dismissLoader();
        Model model = new Model();
        if (model.getLastApiHit() == Constants.UPDATE_PROFILE) {
            AlertDialog("Profile Updated Successfully",ProfileActivity.this);
        }
    }

    @Override
    public void onFetchFailed() {
        dismissLoader();

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data) {

        dismissLoader();
        AlertDialog("Error: " + message,ProfileActivity.this);
    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {

        dismissLoader();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        availabilty = avail[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}