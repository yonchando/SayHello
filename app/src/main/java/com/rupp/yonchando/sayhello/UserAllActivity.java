package com.rupp.yonchando.sayhello;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAllActivity extends AppCompatActivity {

    private RecyclerView userListRecyclerView;
    private FirebaseDatabase database;
    private DatabaseReference mReference;
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all);

        // Database
        database = FirebaseDatabase.getInstance();
        mReference = database.getReference().child("Users");
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User All");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userListRecyclerView = findViewById(R.id.userListRecyclerView);
        userListRecyclerView.setHasFixedSize(true);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Users>().setQuery(mReference, Users.class).build();

        FirebaseRecyclerAdapter<Users, UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder userHolder, int position, @NonNull Users users) {

                userHolder.setName(users.getUsername());
                userHolder.setEmail(users.getEmail());

            }

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }
        };

        userListRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View viewItem;

        public UserViewHolder(View itemView) {
            super(itemView);

            viewItem = itemView;
        }

        public void setName(String username) {
            TextView userListUsernameTextView = viewItem.findViewById(R.id.userListUsernameTextView);
            userListUsernameTextView.setText(username);
        }

        public void setEmail(String email) {
            TextView userListEmailTextView = viewItem.findViewById(R.id.userListEmailTextView);
            userListEmailTextView.setText(email);
        }
    }
}
