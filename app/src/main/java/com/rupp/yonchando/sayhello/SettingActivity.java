package com.rupp.yonchando.sayhello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "UserData";
    private FirebaseAuth firebaseAuth;
    private TextView settingUsernameTextView;
    private TextView settingEmailTextView;
    private TextView settingPhoneNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbarSetting = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarSetting);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Setting");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // View Field
        settingUsernameTextView = findViewById(R.id.settingUsername);
        settingEmailTextView = findViewById(R.id.settingEmail);
        settingPhoneNumberText = findViewById(R.id.settingPhone);

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get Current User
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            Query query = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("username").getValue() != null) {
                        String username = dataSnapshot.child("username").getValue(String.class);
                        settingUsernameTextView.setText(username);
                    }

                    if (dataSnapshot.child("email").getValue() != null) {
                        String email = dataSnapshot.child("email").getValue().toString();
                        settingEmailTextView.setText(email);
                    }

                    if (dataSnapshot.child("phoneNumber").getValue() != null) {
                        String phoneNumber = dataSnapshot.child("phoneNumber").getValue().toString();
                        settingPhoneNumberText.setText(phoneNumber);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
