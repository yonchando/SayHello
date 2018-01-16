package com.rupp.yonchando.sayhello;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PC User on 15-Jan-18.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private Users[] users;
    private Context context;

    public UsersAdapter(Users[] users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_single, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        Users user = users[position];
        Log.d("UserAdapter",user.toString());
        holder.setName(user.getUsername());
        holder.setEmail(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    public Users getUsers(int position) {
        return users[position];
    }

    public void setUsers(Users[] users) {
        this.users = users;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UserViewHolder(View itemView) {
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
