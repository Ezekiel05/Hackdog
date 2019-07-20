package com.example.shopping;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnSignup;

    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnLogin = findViewById(R.id.login);
        btnSignup = findViewById(R.id.signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.equals(btnLogin)){
            if(username.getText() == null || password.getText() == null){
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }
    }
}
