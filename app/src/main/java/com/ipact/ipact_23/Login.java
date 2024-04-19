package com.ipact.ipact_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button btnLog, regNow;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.email); //press ctrl and click
        editTextPassword = findViewById(R.id.password);
        btnLog = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progBar);
        regNow = findViewById(R.id.signup_screen);

        //View view1 = findViewById(R.id.imageView); // Replace with your view
        View view2 = findViewById(R.id.textView);
        View view3 = findViewById(R.id.slogan_name);
        View view4 = findViewById(R.id.password);
        View view5 = findViewById(R.id.login_btn);

        regNow = findViewById(R.id.signup_screen);

        regNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignUp.class);

                // Define the shared elements using Pair
                //Pair<View, String> pair1 = Pair.create(view1, "logo_image");
                Pair<View, String> pair2 = Pair.create(view2, "logo_text");
                Pair<View, String> pair3 = Pair.create(view3, "slogan_tran");
                Pair<View, String> pair4 = Pair.create(view4, "password_tran");
                Pair<View, String> pair5 = Pair.create(view5, "button_tran");

                // Create ActivityOptionsCompat with the shared elements
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Login.this, pair2, pair3, pair4, pair5);

                // Start the second activity with the shared element transition*/
                startActivity(intent, options.toBundle());


            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password;
                email = editTextEmail.getText().toString(); //or use String.valueOf(editTextEmail.getText())
                password = editTextPassword.getText().toString();

                progressBar.setVisibility(View. VISIBLE);

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this,"Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

}
