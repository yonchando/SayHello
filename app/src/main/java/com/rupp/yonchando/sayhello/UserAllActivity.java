package com.rupp.yonchando.sayhello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class UserAllActivity extends AppCompatActivity {

    private RecyclerView userListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all);


        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User All");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userListRecyclerView = findViewById(R.id.userListRecyclerView);
        userListRecyclerView.setHasFixedSize(true);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
