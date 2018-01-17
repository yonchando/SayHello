package com.rupp.yonchando.sayhello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView profileUsername;
    private TextView profilePhoneNumber;
    private TextView profileEmail;
    private Button profileSendSmsButton;
    private String user_id;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // View Field
        profileUsername = findViewById(R.id.settingUsername);
        profileEmail = findViewById(R.id.profileEmail);
        profilePhoneNumber = findViewById(R.id.settingPhone);
        profileSendSmsButton = findViewById(R.id.profileSendSmsButton);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("View User");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //------    Get User to Display Profile -----//
        user_id = getIntent().getStringExtra("user_id");

        query = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String username = null;
                String email = null;
                String phoneNumber = null;

                if (dataSnapshot.child("username").getValue() != null) {
                    username = dataSnapshot.child("username").getValue(String.class);
                }

                if (dataSnapshot.child("email").getValue() != null) {
                    email = dataSnapshot.child("email").getValue(String.class);
                }

                if (dataSnapshot.child("phoneNumber").getValue() != null) {
                    phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                }

                profileUsername.setText(username);
                profileEmail.setText(email);
                profilePhoneNumber.setText(phoneNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error", databaseError.toException().toString());
            }
        });

        //-------- End Profile ----------//


        // Sending SMS

        profileSendSmsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == profileSendSmsButton) {
            startNewActivity(ChatActivity.class);
        }

    }


    private void startNewActivity(Class activityClass) {
        Intent intent = new Intent(ProfileActivity.this, activityClass);
        startActivity(intent);
    }
}
