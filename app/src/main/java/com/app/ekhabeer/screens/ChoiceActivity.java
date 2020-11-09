package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.ekhabeer.R;

public class ChoiceActivity extends AppCompatActivity {

    RelativeLayout rl_login,rl_signup;
    RadioButton radioButton;
    String selected_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        initView();
    }

    public void initView(){

        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        rl_signup = (RelativeLayout) findViewById(R.id.rl_signup);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                if (radioButton.getText().equals("User")){
                    selected_value = "User";
                }else{
                    selected_value = "Consultant";
                }
            }
        });

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!selected_value.equals("")){

                    if (selected_value.equals("User")){

                        Intent intent = new Intent(ChoiceActivity.this,UserLoginActivity.class);
                        startActivity(intent);

                    }else{

                        Intent intent = new Intent(ChoiceActivity.this,ConsultLoginActivity.class);
                        startActivity(intent);

                    }

                }else{
                    Toast.makeText(ChoiceActivity.this, "Select a value", Toast.LENGTH_SHORT).show();
                }

            }
        });

        rl_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!selected_value.equals("")){

                    if (selected_value.equals("User")){

                        Intent intent = new Intent(ChoiceActivity.this,UserRegistrationActivity.class);
                        startActivity(intent);

                    }else{

                        Intent intent = new Intent(ChoiceActivity.this,ClientRegistrationActivity.class);
                        startActivity(intent);

                    }

                }else{
                    Toast.makeText(ChoiceActivity.this, "Select a value", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}