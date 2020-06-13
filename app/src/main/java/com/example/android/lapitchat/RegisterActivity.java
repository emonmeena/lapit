package com.example.android.lapitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText mUserName;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private Button mCreateBtn;

    private Toolbar mToolbar;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    // FIREBASE AUTH
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Toolbar Set
        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        // FIREBASE AUTH
        mAuth = FirebaseAuth.getInstance();

        mUserName = (TextInputEditText) findViewById(R.id.reg_display_name);
        mEmail = (TextInputEditText) findViewById(R.id.login_email);
        mPassword = (TextInputEditText) findViewById(R.id.login_password);
        mCreateBtn = (Button) findViewById(R.id.login_btn);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String user_name = mUserName.getText().toString();
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                mRegProgress.setTitle("Registering User");
                mRegProgress.setMessage("Please wait while we create your account.");
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.show();

                register_user(user_name, email, password);
            }

            }
        });
    }

    private void register_user(String user_name, String email, String password) {
       mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                mRegProgress.dismiss();

                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
            else{
                mRegProgress.hide();
                Toast.makeText(RegisterActivity.this, "Please fill different valid email.", Toast.LENGTH_LONG).show();
            }
           }
       });
    }
}