package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        String type = getIntent().getStringExtra("type");
    }
}