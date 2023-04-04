package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddAddressActivity extends AppCompatActivity {
    private EditText mName;
    private EditText mAddress;
    private EditText mCity;
    private EditText mCode;
    private EditText mNumber;
    private Button mAddAddressBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar mToolbar = findViewById(R.id.AddAddress_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mName = findViewById(R.id.ad_name);
        mAddress = findViewById(R.id.ad_address);
        mCity = findViewById(R.id.ad_city);
        mCode = findViewById(R.id.ad_postal);
        mNumber = findViewById(R.id.ad_phone);
        mAddAddressBtn = findViewById(R.id.add_Addressbtn);
        mstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();




        mAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String city = mCity.getText().toString();
                String code = mCode.getText().toString();
                String address = mAddress.getText().toString();
                String number = mNumber.getText().toString();
                String final_address = "";
                if(!name.isEmpty()){
                    final_address+=name+", ";
                }
                if(!city.isEmpty()){
                    final_address+=city+", ";
                }
                if(!address.isEmpty()){
                    final_address+=address+"";
                }
                if(!code.isEmpty()){
                    final_address+=code+", ";
                }
                if(!number.isEmpty()){
                    final_address+=number+".";
                }
                Map<String,String> mMap = new HashMap<>();
                mMap.put("address", final_address);
                mstore.collection("User").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .collection("Address").add(mMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NotNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddAddressActivity.this,"Address Added",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

            }
        });
    }
}