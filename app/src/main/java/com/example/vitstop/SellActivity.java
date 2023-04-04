package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SellActivity extends AppCompatActivity {
    private EditText itemName;
    private EditText itemDesc;
    private EditText itemURL;
    private EditText itemPrice;
    private EditText itemCat;
    private Button SellItm;
    private FirebaseFirestore mstore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        itemName = findViewById(R.id.itm_name);
        itemDesc = findViewById(R.id.itm_description);
        itemURL = findViewById(R.id.image_url);
        itemPrice = findViewById(R.id.itm_price);
        itemCat = findViewById(R.id.itm_type);
        SellItm = findViewById(R.id.sell_itm_btn);
        mstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        SellItm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = itemName.getText().toString();
                String description = itemDesc.getText().toString();
                double price =  Double.parseDouble(itemPrice.getText().toString());
                String ImageURL = itemURL.getText().toString();
                String ItemType = itemCat.getText().toString();
                Map<String,Object> ItemMap = new HashMap<>();
                ItemMap.put("description",description);
                ItemMap.put("img_url",ImageURL);
                ItemMap.put("name",name);
                ItemMap.put("price",price);
                ItemMap.put("rating",4);
                ItemMap.put("type",ItemType);
                mstore.collection("User").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).collection("Sold")
                        .add(ItemMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(Task<DocumentReference> task) {
                        Toast.makeText(SellActivity.this,"Item Added Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                mstore.collection("AllItems").add(ItemMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(Task<DocumentReference> task) {
                        finish();
                    }
                });


            }
        });



    }
}