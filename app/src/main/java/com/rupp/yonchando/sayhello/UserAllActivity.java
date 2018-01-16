package com.rupp.yonchando.sayhello;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserAllActivity extends AppCompatActivity {

    private UsersAdapter usersAdapter;
    RecyclerView userListRecyclerView;
    private FirebaseRecyclerAdapter<Users, UserViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user != null ? user.getUid() : null;

        // Database
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(20);
        //  Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("User All");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userListRecyclerView = findViewById(R.id.userListRecyclerView);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Users model) {
                holder.setEmail(model.getEmail());
                holder.setName(model.getUsername());
            }

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_single, parent, false);
                return new UserViewHolder(view);
            }
        };
        userListRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        View mView;

        UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String username) {
            TextView userListUsernameTextView = mView.findViewById(R.id.userListUsernameTextView);
            userListUsernameTextView.setText(username);
        }

        public void setEmail(String email) {
            TextView userListEmailTextView = mView.findViewById(R.id.userListEmailTextView);
            userListEmailTextView.setText(email);
        }
    }
}
