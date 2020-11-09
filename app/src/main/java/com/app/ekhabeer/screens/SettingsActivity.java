package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.utils.BaseActivity;

public class SettingsActivity extends BaseActivity {

    EditText et_old_pass,et_new_pass,et_conf_pass;
    RelativeLayout rl_change_pass;
    ImageView iv_back;
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void initView(){

        et_old_pass = (EditText) findViewById(R.id.et_old_pass);
        et_new_pass = (EditText) findViewById(R.id.et_new_pass);
        et_conf_pass = (EditText) findViewById(R.id.et_conf_pass);
        rl_change_pass = (RelativeLayout) findViewById(R.id.rl_change_pass);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_toolbar_title.setText(R.string.menu_settings);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}