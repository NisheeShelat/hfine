package com.medicalapp.hfine;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import android.widget.Toast;
import android.widget.EditText;

import android.widget.Button;
//import android.support.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

//import androidx.annotation.NonNull;
//
//
//import android.os.Bundle;
//
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private EditText emailTextView, passwordTextView;
    private Button Btn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_2);
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = (EditText) findViewById(R.id.EmailAddress);
        passwordTextView = (EditText) findViewById(R.id.Password);
        Btn = (Button) findViewById(R.id.Login);

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
    {
        // Take the value of two edit texts in Strings
        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                     Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(login.this,
                                            Home.class);
                                    startActivity(intent);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
    }
}
