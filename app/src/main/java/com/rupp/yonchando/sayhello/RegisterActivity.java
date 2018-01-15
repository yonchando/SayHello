package com.rupp.yonchando.sayhello;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private TextView textViewAccountExist;
    private EditText editTextUsername;
    private EditText editTextPhoneNumber;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private EditText editTextConfirmPassword;
    private FirebaseDatabase database;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // FirebaseDatabase
        database = FirebaseDatabase.getInstance();

        Toolbar toolbarReg = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarReg);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textViewAccountExist = findViewById(R.id.textViewAccountExist);
        buttonRegister = findViewById(R.id.buttonRegister);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextComfirmPassword);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(this);

        textViewAccountExist.setOnClickListener(this);
    }

    private void startNewActivity(Class activity) {
        hideProgressDialog();
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        if (view == buttonRegister) {
            userRegister();
        }

        if (view == textViewAccountExist) {

            startNewActivity(LoginActivity.class);

        }
    }

    public void showProgressDialog() {
        progressDialog.setMessage("Register Processing..");
        progressDialog.show();
    }

    public void hideProgressDialog() {
        progressDialog.hide();
    }

    private void userRegister() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // User Not Input Email Show Message
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        // User Not Input Password Show Message
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    insertUserInfoToDatabase();
                } else {
                    hideProgressDialog();
                    Toast.makeText(RegisterActivity.this, "Register Fail, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loginAfterRegister(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    progressDialog.dismiss();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Authentication failed. Please try again", Toast.LENGTH_SHORT).show();
                    startNewActivity(LoginActivity.class);
                }
            }
        });
    }

    private void insertUserInfoToDatabase() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            String username = editTextUsername.getText().toString().trim();
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();
            final String email = editTextEmail.getText().toString().trim();
            final String password = editTextPassword.getText().toString().trim();
            mReference = database.getReference().child("Users").child(uid);
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("username", username);
            hashMap.put("email", user.getEmail());
            hashMap.put("photo","@string/ic_profile_image_default");
            hashMap.put("phoneNumber", phoneNumber);
            hashMap.put("status", "use");
            mReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loginAfterRegister(email, password);
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
