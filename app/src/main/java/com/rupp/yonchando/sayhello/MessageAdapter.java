package com.rupp.yonchando.sayhello;

import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PC User on 21-Jan-18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> messageList;
    private FirebaseAuth userAuth;

    public MessageAdapter(List<Messages> messageList) {

        this.messageList = messageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        userAuth = FirebaseAuth.getInstance();
        FirebaseUser user = userAuth.getCurrentUser();
        Messages message = messageList.get(position);

        if (user != null) {
            String currentUserID = user.getUid();
            String from_user = message.getFrom();
            if (from_user.equals(currentUserID)) {
                holder.messageTextView.setTextColor(Color.BLACK);
                holder.messageTextView.setBackgroundResource(R.drawable.message_text_background);
            }else {
                holder.messageTextView.setTextColor(Color.BLACK);
                holder.messageTextView.setBackgroundColor(Color.WHITE);
            }
        }

        holder.messageTextView.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageTextView;
        private CircleImageView profileImage;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.messageText);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
