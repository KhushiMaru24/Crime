package com.example.crime;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;

    private static final String PREF_NAME = "MyPrefs";
    private static final String PREF_KEY_USERNAME = "username";
    private static final String PREF_KEY_PASSWORD = "password";
    private static final String PREF_KEY_REMEMBER_ME = "rememberMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        // Load the "Remember Me" state and user credentials from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean(PREF_KEY_REMEMBER_ME, false);

        if (rememberMe) {
            String savedUsername = sharedPreferences.getString(PREF_KEY_USERNAME, "");
            String savedPassword = sharedPreferences.getString(PREF_KEY_PASSWORD, "");

            usernameEditText.setText(savedUsername);
            passwordEditText.setText(savedPassword);
            rememberMeCheckBox.setChecked(true);
        }

        // Set an OnClickListener for the login button
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user-entered username and password
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Save the user credentials and "Remember Me" state in SharedPreferences if the checkbox is checked
                if (rememberMeCheckBox.isChecked()) {
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
}
