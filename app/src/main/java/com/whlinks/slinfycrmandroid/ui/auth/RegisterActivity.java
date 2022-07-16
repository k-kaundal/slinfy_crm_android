package com.whlinks.slinfycrmandroid.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.whlinks.slinfycrmandroid.MainActivity;
import com.whlinks.slinfycrmandroid.R;

public class RegisterActivity extends AppCompatActivity {
EditText emaiText ,passText;
Button register;
TextView login;
FirebaseAuth auth;
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emaiText = findViewById(R.id.email);
        passText = findViewById(R.id.pass);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emaiText.getText().toString().trim();
                String pass = passText.getText().toString().trim();
                if(email.isEmpty()){
                    emaiText.setError("Enter valid email");
                    return;
                }
                if (pass.isEmpty()){
                    passText.setError("Enter a valid password");
                    return;
                }

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            user = auth.getCurrentUser();
                            if(user.isEmailVerified()){
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this, "Email Not Verified Check your email to verify your accoutn!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "Registration Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}