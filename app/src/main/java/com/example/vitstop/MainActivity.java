package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }
    public void GoToLogin(View view){
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        startActivity(intent);
    }
    public void GoToRegister(View view){
        Intent intent = new Intent(MainActivity.this, SignIn.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }
}