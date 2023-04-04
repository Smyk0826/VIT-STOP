package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.vitstop.Features.Address;
import com.example.vitstop.Features.Items;
import com.example.vitstop.Features.Popular;
import com.example.vitstop.adapter.AddressAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    private RecyclerView mAddressRecyclerView;
    private Button paymentBtn;
    private Button mAddAddress;
    private AddressAdapter mAddressAdapter;
    private List<Address> mAddressList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    String address = "";
    private Toolbar actionBar;
    Popular popular = null;
    Items items = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object obj = getIntent().getSerializableExtra("item");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Toolbar mToolbar = findViewById(R.id.Address_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddressRecyclerView = findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
        mAddAddress = findViewById(R.id.add_Address_btn);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAddressList = new ArrayList<>();
        mAddressAdapter= new AddressAdapter(getApplicationContext(),mAddressList,this);
        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAddressRecyclerView.setAdapter(mAddressAdapter);

        mStore.collection("User").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).collection("Address")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc:task.getResult()){
                        Address address = doc.toObject(Address.class);
                        mAddressList.add(address);
                        mAddressAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        mAddAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount = 0.0;
                String url = "";
                String itemName = "";
                if(obj instanceof Popular){
                    popular = (Popular) obj;
                    amount = popular.getPrice();
                    url = popular.getImg_url();
                    itemName = popular.getName();
                }
                if(obj instanceof Items){
                    items = (Items) obj;
                    amount = items.getPrice();
                    url = items.getImg_url();
                    itemName = items.getName();
                }
                Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount", amount);
                intent.putExtra("img_url", url);
                intent.putExtra("name", itemName);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });

    }

    @Override
    public void setAddress(String s) {
        address = s;
    }
}