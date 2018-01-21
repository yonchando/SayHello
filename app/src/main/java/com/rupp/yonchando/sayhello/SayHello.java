package com.rupp.yonchando.sayhello;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by PC User on 21-Jan-18.
 */

public class SayHello extends Application {

    private FirebaseAuth authUser;
    private FirebaseUser user;
    private DatabaseReference userData;
    private FirebaseDatabase database;
    private String user_id;


    @Override
    public void onCreate() {
        super.onCreate();
        authUser = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = authUser.getCurrentUser();
        if (user != null) {
            user_id = user.getUid();
            userData = database.getReference().child("Users").child(user_id);
            if (userData != null) {
                userData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userData.child("online").onDisconnect().setValue("false");
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Error", "Error:" + databaseError.toException());
                    }
                });
            }
        }
    }
}
