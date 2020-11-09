package com.app.ekhabeer.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.ConsultationsAdapter;
import com.app.ekhabeer.adapters.InboxAdapter;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;
import com.app.ekhabeer.utils.PrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,Controller.APICallbackListener {

    private AppBarConfiguration mAppBarConfiguration;
    LinearLayout ll_law,ll_bussiness,ll_family_kids,ll_diet_fitness,ll_psycology,ll_medical;
    Controller mController;
    TextView tv_user_name,tv_consul_cancel,tv_consul_sech,tv_consul_complete,tv_toolbar_name;
    String user_type;
    RelativeLayout rl_login;
    RecyclerView recycler_view_consults;
    ImageView iv_profile,iv_doc;
    ConsultationsAdapter consultationsAdapter;
    LinearLayout ll_user,ll_consultants;
    HashMap<String, String> hashMapMain;
    NavigationView navigationView;
    String status_type;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.clear();
//        editor.apply();
        user_type = prefs.getString("user_type","");
        Log.e("HomeActivity","user_type>>" + user_type);
        PrefManager prefManager = new PrefManager(getApplicationContext());
        if(prefManager.isFirstTimeLaunch()){
            Log.e("checkIntro","main>>");
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(HomeActivity.this, WelcomeActivity.class));
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        tv_user_name = (TextView) headerView.findViewById(R.id.tv_user_name);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        tv_toolbar_name = (TextView) findViewById(R.id.tv_toolbar_name);

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.ic_placeholder);
//        Glide.with(HomeActivity.this)
//                .setDefaultRequestOptions(requestOptions)
//                .load(Constants.IMAGE_BASE_URL + Constants.PROFILE)
//                .into(iv_doc);

        initView();
    }

    public void initView(){

        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        ll_law = (LinearLayout) findViewById(R.id.ll_law);
        ll_bussiness = (LinearLayout) findViewById(R.id.ll_bussiness);
        ll_family_kids = (LinearLayout) findViewById(R.id.ll_family_kids);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        ll_diet_fitness = (LinearLayout) findViewById(R.id.ll_diet_fitness);
        ll_psycology = (LinearLayout) findViewById(R.id.ll_psycology);
        ll_medical = (LinearLayout) findViewById(R.id.ll_medical);
        ll_user = (LinearLayout) findViewById(R.id.ll_user);
        recycler_view_consults = (RecyclerView) findViewById(R.id.recycler_view_consults);
        ll_consultants = (LinearLayout) findViewById(R.id.ll_consultants);

        prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
        tv_toolbar_name.setText("Hi " + prefs.getString("fname",""));


        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                intent.putExtra("user_type",user_type);
                startActivity(intent);

            }
        });
//        UserProfileAPI();

        if (user_type.equals("user")){

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            ll_user.setVisibility(View.VISIBLE);
            rl_login.setVisibility(View.VISIBLE);
            ll_consultants.setVisibility(View.GONE);
            Log.e("checkMenu",">user>");
        }else{

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.consultant_menu);
            rl_login.setVisibility(View.GONE);
            ll_user.setVisibility(View.GONE);
            ll_consultants.setVisibility(View.VISIBLE);
            recycler_view_consults.setHasFixedSize(true);
            recycler_view_consults.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recycler_view_consults.setAdapter(consultationsAdapter);
            GetConsultationsAPI("scheduled");

            tv_consul_sech = (TextView) findViewById(R.id.tv_consul_sech);
            tv_consul_cancel = (TextView) findViewById(R.id.tv_consul_cancel);
            tv_consul_complete = (TextView) findViewById(R.id.tv_consul_complete);

            tv_consul_sech.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_consul_sech.setTextColor(getResources().getColor(R.color.colorBlack));
                    tv_consul_cancel.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_consul_complete.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_consul_sech.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                    tv_consul_cancel.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                    tv_consul_complete.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                    GetConsultationsAPI("scheduled");
                }
            });
            tv_consul_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_consul_sech.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_consul_cancel.setTextColor(getResources().getColor(R.color.colorBlack));
                    tv_consul_complete.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_consul_sech.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                    tv_consul_cancel.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                    tv_consul_complete.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                    GetConsultationsAPI("cancelled");

                }
            });
            tv_consul_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_consul_sech.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_consul_cancel.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_consul_complete.setTextColor(getResources().getColor(R.color.colorBlack));
                    tv_consul_sech.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                    tv_consul_cancel.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                    tv_consul_complete.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                    GetConsultationsAPI("completed");

                }
            });

            Log.e("checkMenu",">consultant>");
        }

        ll_psycology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("cat_id","1");
                intent.putExtra("type","Psycology");
                startActivity(intent);
            }
        });
        ll_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("cat_id","2");
                intent.putExtra("type","Medical");
                startActivity(intent);
            }
        });
        ll_diet_fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("cat_id","3");
                intent.putExtra("type","Diet and Fitness");
                startActivity(intent);
            }
        });
        ll_family_kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("cat_id","4");
                intent.putExtra("type","Family and Kids");
                startActivity(intent);
            }
        });
        ll_bussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("cat_id","5");
                intent.putExtra("type","Bussiness and investements");
                startActivity(intent);
            }
        });
        ll_law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                intent.putExtra("cat_id","6");
                intent.putExtra("type","Law & Legislation");
                startActivity(intent);
            }
        });

    }

    public void UserProfileAPI(){

        hashMapMain.put(Constants.GET_PROFILE_INFO , "");
        hashMapMain.put(Constants.TYPE , user_type);
        hashMapMain.put(Constants.ID , prefs.getString("user_id",""));
        Log.e("checkUserProfileAPI",">>" + hashMapMain);
        mController.UserProfileAPI(hashMapMain,HomeActivity.this);

    }

    public void GetConsultationsAPI(String status_type){

        hashMapMain.put(Constants.APPOINTMENT_LIST_CONSUL , "");
        hashMapMain.put(Constants.STATUS_TYPE , status_type);
        hashMapMain.put(Constants.CONSULTANT_ID , prefs.getString("user_id",""));
        Log.e("checkUserProfileAPI",">>" + hashMapMain);
        mController.GetConsultaionsForConsultants(hashMapMain,HomeActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        if (navigationView.getMenu().equals(R.menu.consultant_menu)){

            int id = item.getItemId();

            if (id == R.id.nav_inbox) {
                Intent intent = new Intent(HomeActivity.this, InboxActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_payment) {

            }else if (id == R.id.nav_users) {
                Intent intent = new Intent(HomeActivity.this, UsersActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_invite) {
                Intent intent = new Intent(HomeActivity.this, InviteFriendsActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_happy) {
                Intent intent = new Intent(HomeActivity.this, HappinessActivity.class);
                startActivity(intent);

            }else if (id == R.id.nav_gift) {
                Intent intent = new Intent(HomeActivity.this, GiftActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_settings) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_logout) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(HomeActivity.this,ChoiceActivity.class);
                startActivity(intent);
                finish();
                Log.e("checkMyApp","nav_logout>>" + prefs.getString("user_id",""));
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }else{

            int id = item.getItemId();

            if (id == R.id.nav_consul) {
                Intent intent = new Intent(HomeActivity.this, ConsultationsActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_inbox) {
                Intent intent = new Intent(HomeActivity.this, InboxActivity.class);
//                Intent intent = new Intent(HomeActivity.this, MessagesActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_payment) {

            }else if (id == R.id.nav_invite) {
                Intent intent = new Intent(HomeActivity.this, InviteFriendsActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_happy) {
                Intent intent = new Intent(HomeActivity.this, HappinessActivity.class);
                startActivity(intent);

            }else if (id == R.id.nav_gift) {
                Intent intent = new Intent(HomeActivity.this, GiftActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_settings) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_logout) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(HomeActivity.this,ChoiceActivity.class);
                startActivity(intent);
                finish();
                Log.e("checkMyApp","nav_logout>>" + prefs.getString("user_id",""));
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFetchStart() {

        showLoader(HomeActivity.this);
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {
        try {
            Log.e("checkUserInfo",">>" + pojo.getString("fname"));
            Log.e("checkUserInfo","1>>" + Constants.model.getLastApiHit());
            if (Constants.model.getLastApiHit().equals(Constants.GET_PROFILE_INFO)){
                tv_user_name.setText(pojo.getString("fname"));
//                if (user_type.equals("user")){
//
//                    Log.e("checkUserInfo","if>>");
//                }else{
//                    Log.e("checkUserInfo","else>>");
//                    GetConsultationsAPI("scheduled");
//                }
            }


//            else if (Constants.model.getLastApiHit().equals(Constants.APPOINTMENT_LIST_USER)){
//
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {
        if (Constants.model.getLastApiHit().equals(Constants.APPOINTMENT_LIST_CONSUL)){
            Log.e("checkFninal",">>" + pojoList);

            consultationsAdapter = new ConsultationsAdapter(pojoList,this);
            recycler_view_consults.setAdapter(consultationsAdapter);
        }
    }

    @Override
    public void onFetchComplete() {

        dismissLoader();
    }

    @Override
    public void onFetchFailed() {

        dismissLoader();
    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data) {

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {

    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}