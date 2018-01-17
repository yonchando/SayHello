package com.rupp.yonchando.sayhello;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

    private RecyclerView friendListFragment;
    private FirebaseRecyclerAdapter<Users, UserViewHolder> adapter;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_friend, container, false);

        Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(20);

        friendListFragment = fragment.findViewById(R.id.friendListFragment);
        friendListFragment.setHasFixedSize(true);
        friendListFragment.setLayoutManager(new LinearLayoutManager(getActivity()));


        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull Users model) {
                holder.setEmail(model.getEmail());
                holder.setName(model.getUsername());
                final String user_id = getRef(position).getKey();
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
                        profileIntent.putExtra("user_id", user_id);
                        startActivity(profileIntent);
                    }
                });
            }

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_single, parent, false);
                return new UserViewHolder(view);
            }
        };
        friendListFragment.setAdapter(adapter);


        // Inflate the layout for this fragment
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
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
            TextView userListUsernameTextView = mView.findViewById(R.id.userListUsernameTextView);
            userListUsernameTextView.setText(email);
        }
    }
}
