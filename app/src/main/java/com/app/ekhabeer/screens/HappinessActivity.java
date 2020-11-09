package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HappinessActivity extends BaseActivity implements Controller.APICallbackListener {

    EditText et_feed_name,et_feed_email,et_feed_sub,et_feed_msg;
    TextView tv_pos_feedback,tv_neg_feedback,tv_help_feedback,tv_any_feedback;
    RelativeLayout rl_submit;
    Controller mController;
    String type;
    ImageView iv_back;
    TextView tv_toolbar_title;
    HashMap<String, String> hashMapMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happiness);

        initView();
    }

    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();

        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        tv_pos_feedback = (TextView) findViewById(R.id.tv_pos_feedback);
        tv_neg_feedback = (TextView) findViewById(R.id.tv_neg_feedback);
        tv_help_feedback = (TextView) findViewById(R.id.tv_help_feedback);
        tv_any_feedback = (TextView) findViewById(R.id.tv_any_feedback);
        et_feed_name = (EditText) findViewById(R.id.et_feed_name);
        et_feed_sub = (EditText) findViewById(R.id.et_feed_sub);
        et_feed_email = (EditText) findViewById(R.id.et_feed_email);
        et_feed_msg = (EditText) findViewById(R.id.et_feed_msg);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_toolbar_title.setText(R.string.menu_happy);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_pos_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "positive";
                tv_any_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_help_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_neg_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_pos_feedback.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_pos_feedback.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_neg_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_help_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_any_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
            }
        });

        tv_neg_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "negative";
                tv_any_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_help_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_neg_feedback.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_pos_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_neg_feedback.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_pos_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_help_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_any_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));

            }
        });

        tv_help_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "improve";
                tv_any_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_help_feedback.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_neg_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_pos_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_help_feedback.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_neg_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_pos_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_any_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));

            }
        });

        tv_any_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "complaint";
                tv_any_feedback.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_help_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_neg_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_pos_feedback.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_any_feedback.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_neg_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_help_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_pos_feedback.setBackgroundColor(getResources().getColor(R.color.colorRedText));

            }
        });

        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendFeedback();
            }
        });
    }

    public void SendFeedback(){
        hashMapMain.put(Constants.INSERT_FEEDBACK , "");
//        hashMapMain.put(Constants.USER_ID , et_email.getText().toString());
//        hashMapMain.put(Constants.CONSULTANT_ID , et_pass.getText().toString());
        hashMapMain.put(Constants.EMAIL , et_feed_email.getText().toString());
        hashMapMain.put(Constants.RATING , "");
        hashMapMain.put(Constants.NAME , et_feed_name.getText().toString());
        hashMapMain.put(Constants.DATE , "30/08/2020");
        hashMapMain.put(Constants.TIME , "");
        hashMapMain.put(Constants.TYPE , type);
        hashMapMain.put(Constants.REVIEW_TEXT , et_feed_msg.getText().toString());
        mController.SendFeedback(hashMapMain,HappinessActivity.this);

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
}