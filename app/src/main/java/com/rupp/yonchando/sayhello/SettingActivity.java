package com.rupp.yonchando.sayhello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private DatabaseReference userReference;
    private TextView settingUsernameTextView;
    private TextView settingEmailTextView;
    private TextView settingPhoneNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbarSetting = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarSetting);
        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // View Field
        settingUsernameTextView = findViewById(R.id.userListUsernameTextView);
        settingEmailTextView = findViewById(R.id.userListEmailTextView);
        settingPhoneNumberText = findViewById(R.id.settingPhoneNumberText);

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
                    Log.d("dataSnap", "Value" + dataSnapshot);
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
//                    settingUsernameTextView.setText(username);
//                        settingEmailTextView.setText(email);
//                        settingPhoneNumberText.setText(phoneNumber);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
