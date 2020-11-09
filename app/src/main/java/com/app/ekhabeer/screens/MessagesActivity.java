package com.app.ekhabeer.screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.InboxAdapter;
import com.app.ekhabeer.adapters.MessagesAdapter;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;
import com.app.ekhabeer.utils.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessagesActivity extends BaseActivity implements Controller.APICallbackListener {

    MessagesAdapter messagesAdapter;
    RecyclerView recycler_view_messages;
    EditText et_message;
    Button btn_send;
    Controller mController;
    JSONArray final_array;
    String api_user_id,receiver_id,act;
    ArrayList<HashMap<String, String>>  messageList;
    HashMap<String, String> hashMapMain;
    HashMap<String, String> hashMapMainSend;
    ArrayList<Model> data;
    LinearLayoutManager linearLayoutManager;
    ImageView iv_back;
    TextView tv_toolbar_title;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        hashMapMainSend = new HashMap<>();

        data=new ArrayList<>();
        messageList = new ArrayList<>();
        Intent intent = getIntent();
        act = intent.getStringExtra("act");
        if (act.equals("Cadapter")){
            receiver_id = intent.getStringExtra("receiver_id");
        }else if (act.equals("Iadapter")){
            receiver_id = intent.getStringExtra("api_user_id");
        }
//        String new_arl = getIntent().getStringExtra("list");
//        try {
//            final_array = new JSONArray(new_arl);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        messageList = intent.getParcelableArrayListExtra("list");
        Log.e("MessagesActCheck","act>>" + receiver_id);
        recycler_view_messages = (RecyclerView) findViewById(R.id.recycler_view_messages);
        et_message = (EditText) findViewById(R.id.et_message);
        btn_send = (Button) findViewById(R.id.btn_send);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_toolbar_title.setText(R.string.messages);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Log.e("checkFinalMsg",">>" + data);
        GetInbox();
//        messagesAdapter = new MessagesAdapter(data,this);
        recycler_view_messages.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recycler_view_messages.setLayoutManager(linearLayoutManager);
        recycler_view_messages.setAdapter(messagesAdapter);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
                et_message.setText("");
            }
        });

    }

    public void SendMessage(){

        SharedPreferences prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);

        hashMapMainSend.put(Constants.INSERT_MESSAGE , "");
        hashMapMainSend.put(Constants.SENDER_ID , prefs.getString("user_id",""));
        hashMapMainSend.put(Constants.RECEIVER_ID , receiver_id);
        hashMapMainSend.put(Constants.MESSAGE , et_message.getText().toString());
        hashMapMainSend.put(Constants.SENDER_TYPE , prefs.getString("user_type",""));
        Log.e("MessagesActCheck",">SendMessage>" + hashMapMainSend);
        mController.SendMessage(hashMapMainSend,MessagesActivity.this);
    }

        public void GetInbox(){
            SharedPreferences prefs = this.getSharedPreferences(
                    "Ekhabeer", Context.MODE_PRIVATE);
            if (prefs.getString("user_type","").equals("user")){
                hashMapMain.put(Constants.GET_MESSAGES_LIST , "");
                hashMapMain.put(Constants.USER_ID , prefs.getString("user_id",""));
                hashMapMain.put(Constants.CONSULTANT_ID , receiver_id);
                Log.e("MessagesActCheck",">GetInbox>" + hashMapMain);
                mController.GetInboxList(hashMapMain,MessagesActivity.this);

            }else{
                hashMapMain.put(Constants.GET_MESSAGES_LIST , "");
                hashMapMain.put(Constants.USER_ID , receiver_id);
                hashMapMain.put(Constants.CONSULTANT_ID , prefs.getString("user_id",""));
                Log.e("MessagesActCheck",">GetInbox>" + hashMapMain);
                mController.GetInboxList(hashMapMain,MessagesActivity.this);

            }

    }

    @Override
    public void onFetchStart() {

        showLoader(this);
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {
        Log.e("MessagesActCheck",">onFetchProgress>" + pojoList + "><><" + jsonArrays);
        data.clear();


        Model model = new Model();

        if (Constants.model.getLastApiHit().equals(Constants.INSERT_MESSAGE)){
            Log.e("MessagesActCheck",">if>" + pojoList + "><><" + jsonArrays);
            GetInbox();
        }else if (Constants.model.getLastApiHit().equals(Constants.GET_MESSAGES_LIST)){
            Log.e("MessagesActCheck",">else>" + pojoList + "><><" + jsonArrays);
            JSONArray jArray = null;
            try {
                jArray = jsonArrays;
                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    model = new Model();
                    model.setId(json_data.getString(Constants.ID));
                    model.setSender_id(json_data.getString(Constants.SENDER_ID));
                    model.setMessage(json_data.getString(Constants.MESSAGE));
                    model.setTime(json_data.getString(Constants.TIME));
                    data.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            messagesAdapter = new MessagesAdapter(data,this);
            recycler_view_messages.setAdapter(messagesAdapter);
            linearLayoutManager.scrollToPosition(data.size() -1);
            dismissLoader();
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
        dismissLoader();
    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {
        dismissLoader();
    }
}