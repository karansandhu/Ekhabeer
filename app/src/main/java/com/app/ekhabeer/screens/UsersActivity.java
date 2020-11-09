package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.InboxAdapter;
import com.app.ekhabeer.adapters.UserAdapter;
import com.app.ekhabeer.utils.BaseActivity;

import java.util.ArrayList;

public class UsersActivity extends BaseActivity {

    UserAdapter UserAdapter;
    RecyclerView recycler_view_users;
    TextView tv_user_review,tv_user_unhappy,tv_user_repeat;
    ArrayList<String> userList;
    ImageView iv_back;
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        initView();
    }

    public void initView(){
        userList = new ArrayList<>();
        tv_user_review = (TextView) findViewById(R.id.tv_user_review);
        tv_user_unhappy = (TextView) findViewById(R.id.tv_user_unhappy);
        tv_user_repeat = (TextView) findViewById(R.id.tv_user_repeat);
        recycler_view_users = (RecyclerView) findViewById(R.id.recycler_view_users);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        tv_toolbar_title.setText(R.string.users);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        userList.add("karan");
        userList.add("Anubhav");
        userList.add("Rajveer");
        userList.add("Hello");

        recycler_view_users.setHasFixedSize(true);
        recycler_view_users.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        UserAdapter = new UserAdapter(userList,this);
        recycler_view_users.setAdapter(UserAdapter);
        tv_user_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_user_review.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_user_unhappy.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_user_repeat.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_user_review.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_user_unhappy.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_user_repeat.setBackgroundColor(getResources().getColor(R.color.colorRedText));
            }
        });
        tv_user_unhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_user_review.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_user_unhappy.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_user_repeat.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_user_review.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_user_unhappy.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_user_repeat.setBackgroundColor(getResources().getColor(R.color.colorRedText));

            }
        });
        tv_user_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_user_review.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_user_unhappy.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_user_repeat.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_user_review.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_user_unhappy.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_user_repeat.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));

            }
        });
    }

}