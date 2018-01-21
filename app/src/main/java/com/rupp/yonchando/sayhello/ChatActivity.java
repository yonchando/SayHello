package com.rupp.yonchando.sayhello;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private String currentUser_id;
    private String user_id;
    private DatabaseReference mRootDatabase;
    private ImageButton chatSendMessageButton;
    private EditText chatMessageEditText;
    private List<Messages> messagesList = new ArrayList<>();
    MessageAdapter messageAdapter;
    private DatabaseReference messageData;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chatSendMessageButton = findViewById(R.id.chatSendMessageButton);
        chatMessageEditText = findViewById(R.id.chatMessageEditText);

        // Adapter Message
        messageAdapter = new MessageAdapter(messagesList);

        //------- RecycleView ---- //
        RecyclerView messageListRecycle = findViewById(R.id.messageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messageListRecycle.setHasFixedSize(true);
        messageListRecycle.setLayoutManager(layoutManager);
        messageListRecycle.setAdapter(messageAdapter);
        //------------- User ----------//
        user_id = getIntent().getStringExtra("user_id");
        String username = getIntent().getStringExtra("username");
        if (getSupportActionBar() != null) {
            setTitle(user_id);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        database = FirebaseDatabase.getInstance();
        mRootDatabase = database.getReference();
        FirebaseUser curentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (curentUser != null) {
            currentUser_id = curentUser.getUid();
        }

        mRootDatabase.child("Chat").child(currentUser_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(user_id)) {
                    Map<String, Object> chatAddMap = new HashMap<>();
                    chatAddMap.put("seen", "false");
                    chatAddMap.put("timestamps", ServerValue.TIMESTAMP);
                    Map<String, Object> chatUserMap = new HashMap<>();
                    chatUserMap.put("Chat/" + currentUser_id + "/" + user_id, chatAddMap);
                    chatUserMap.put("Chat/" + user_id + "/" + currentUser_id, chatAddMap);
                    mRootDatabase.updateChildren(chatUserMap);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.toException().toString());
            }
        });
        chatSendMessageButton.setOnClickListener(this);
        // Load Messages
        getLoadMessages();

    }

    public void getLoadMessages() {
        mRootDatabase.child("messages").child(user_id).child(currentUser_id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Messages messages = dataSnapshot.getValue(Messages.class);
                messagesList.add(messages);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == chatSendMessageButton) {
            sendMessage();
        }

    }

    private void sendMessage() {

        String getSendMessage = chatMessageEditText.getText().toString().trim();

        if (!getSendMessage.isEmpty()) {

            String sendUser = "messages/" + currentUser_id + "/" + user_id;
            String recievedUser = "messages/" + user_id + "/" + currentUser_id;

            DatabaseReference messagesData = mRootDatabase.child("messages").child(currentUser_id).child(user_id).push();

            String push_id = messagesData.getKey();

            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("message", getSendMessage);
            messageMap.put("seen", "false");
            messageMap.put("types", "text");
            messageMap.put("from",currentUser_id);
            messageMap.put("timestamp", ServerValue.TIMESTAMP);

            Map<String, Object> messageUserMap = new HashMap<>();
            messageUserMap.put(sendUser + "/" + push_id, messageMap);
            messageUserMap.put(recievedUser + "/" + push_id, messageMap);

            mRootDatabase.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    chatMessageEditText.setText("");
                }
            });


        }

    }

    private void startMainActivity() {
        Intent mainIntent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }
}
