package com.whlinks.slinfycrmandroid.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.whlinks.slinfycrmandroid.R;

public class ForgotActivity extends AppCompatActivity {
    EditText emailText;
    Button send;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        emailText = findViewById(R.id.email);
        send = findViewById(R.id.send);
        auth = FirebaseAuth.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString().trim();
                if (email.isEmpty()) {
                    emailText.setError("Enter valid email id");
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(ForgotActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}