package com.example.android.lapitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    //Progress Dialog
    private ProgressDialog mStatusProgress;

    //Get Toolbar
    private androidx.appcompat.widget.Toolbar mToolbar;

    //Button
    private Button mUpdateStatus;
    private TextInputEditText newStatus;

    //FirebaseDatabase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //Set Toolbar
        mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Update Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String status_value = getIntent().getStringExtra("status_value");   //Receives existing status. Here "status_value  is key

        //Declare Progress
        mStatusProgress = new ProgressDialog(this);

        mUpdateStatus = (Button) findViewById(R.id.status_update_btn);
        newStatus = (TextInputEditText) findViewById(R.id.status_input);

        newStatus.setText(status_value);

        //FirebaseDatabase and current_user
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("status");

        //Event Listener on Button
        mUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedStatus = newStatus.getText().toString();

                mStatusProgress.setTitle("Updating Status");
                mStatusProgress.setMessage("Please wait while we change your status");
                mStatusProgress.setCanceledOnTouchOutside(false);
                mStatusProgress.show();

                mDatabase.setValue(updatedStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mStatusProgress.dismiss();

                            Intent status_changed = new Intent(StatusActivity.this, SettingsActivity.class);
                            status_changed.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(status_changed);
                            finish();
                        }
                    }
                });
            }
        });

    }
}
