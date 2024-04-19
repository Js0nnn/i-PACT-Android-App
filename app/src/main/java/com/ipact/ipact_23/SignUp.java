package com.ipact.ipact_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {

    TextInputEditText regEmail, regPassword, regUserName;
    Button btnReg, logNow;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        regEmail = findViewById(R.id.email); //press ctrl and click
        regPassword = findViewById(R.id.password);
        regUserName = findViewById(R.id.username);
        btnReg = findViewById(R.id.reg_btn);
        progressBar = findViewById(R.id.progressBar);
        logNow = findViewById(R.id.login_page_btn);

        logNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        // ...

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, username;
                email = regEmail.getText().toString();
                password = regPassword.getText().toString();
                username = regUserName.getText().toString();

                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                    Toast.makeText(SignUp.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // User created successfully, update the user profile
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    if (currentUser != null) {
                                        currentUser.updateProfile(new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(username)
                                                        .build())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(SignUp.this, "Account created with username: " + username, Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(SignUp.this, "Failed to update user profile", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    if (task.getException() != null && task.getException().getMessage() != null) {
                                        String errorMessage = task.getException().getMessage();
                                        if (errorMessage.contains("email address is already in use")) {
                                            Toast.makeText(SignUp.this, "This email is already associated with an account.", Toast.LENGTH_LONG).show();
                                        }
                                        else if (errorMessage.contains("The email address is badly formatted")) {
                                            Toast.makeText(SignUp.this, "Invalid email address", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(SignUp.this, errorMessage, Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(SignUp.this, "Unknown error occurred", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
        });

    }
}
