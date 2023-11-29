package com.example.crime;


import static com.example.crime.MainActivity2.PREF_KEY_PASSWORD;
import static com.example.crime.MainActivity2.PREF_KEY_REMEMBER_ME;
import static com.example.crime.MainActivity2.PREF_KEY_USERNAME;
import static com.example.crime.MainActivity2.PREF_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity2 extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn,rb;
    private CheckBox cb,cr;
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;
    static final String PREF_NAME = "MyPrefs";
    static final String PREF_KEY_USERNAME = "username";
    static final String PREF_KEY_PASSWORD = "password";
    static final String PREF_KEY_REMEMBER_ME = "rememberMe";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailTextView = findViewById(R.id.editTextText);
        passwordTextView = findViewById(R.id.editTextText3);
        Btn = findViewById(R.id.button);
        progressbar = findViewById(R.id.progressBar);
        cb = findViewById(R.id.checkBox);
        cr = findViewById(R.id.checkBox3);
        rb = findViewById(R.id.button4);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean(PREF_KEY_REMEMBER_ME, false);

        if (rememberMe) {
            String savedUsername = sharedPreferences.getString(PREF_KEY_USERNAME, "");
            String savedPassword = sharedPreferences.getString(PREF_KEY_PASSWORD, "");

            emailTextView.setText(savedUsername);
            passwordTextView.setText(savedPassword);
            cr.setChecked(true);
        }

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
                Intent intent
                        = new Intent(MainActivity2.this,
                        MainActivity6.class);
                startActivity(intent);
            }
        });
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(MainActivity2.this,
                        MainActivity4.class);
                startActivity(intent);
            }
        });
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(MainActivity2.this,
                        MainActivity3.class);
                startActivity(intent);
            }
        });
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user-entered username and password
                String username = emailTextView.getText().toString();
                String password =passwordTextView.getText().toString();

                // Save the user credentials and "Remember Me" state in SharedPreferences if the checkbox is checked
                if (cr.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PREF_KEY_USERNAME, username);
                    editor.putString(PREF_KEY_PASSWORD, password);
                    editor.putBoolean(PREF_KEY_REMEMBER_ME, true);
                    editor.apply();
                } else {
                    // If "Remember Me" is not checked, clear the stored credentials
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(PREF_KEY_USERNAME);
                    editor.remove(PREF_KEY_PASSWORD);
                    editor.putBoolean(PREF_KEY_REMEMBER_ME, false);
                    editor.apply();
                }

                // TODO: Implement your login logic here
            }
        });
    }

    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading
        progressbar.setVisibility(View.VISIBLE);

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
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(MainActivity2.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);
                                }
                            }
                        });

    }
}
