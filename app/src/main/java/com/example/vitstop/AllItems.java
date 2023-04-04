package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vitstop.Features.Items;
import com.example.vitstop.adapter.AllItemsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllItems extends AppCompatActivity {
    private FirebaseFirestore mstore;
    List<Items> AllitemsList;
    private RecyclerView AllItemsRecyclerView; //all items recycler view
    private AllItemsAdapter allItemsAdapter;
    private Toolbar itemToolbar;
    private int itemNo=0;
    private FirebaseAuth mAuth;
    List<String> ordersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);
        String type = getIntent().getStringExtra("type");
        itemNo = getIntent().getIntExtra("itemNumber",0);
        mstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        AllitemsList = new ArrayList<>();
        ordersList = new ArrayList<>();
        itemToolbar = findViewById(R.id.item_toolbar3);
        setSupportActionBar(itemToolbar);
        getSupportActionBar().setTitle("Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AllItemsRecyclerView = findViewById(R.id.AllItems_Recycler);
        AllItemsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        allItemsAdapter = new AllItemsAdapter(this,AllitemsList);
        AllItemsRecyclerView.setAdapter(allItemsAdapter);
        if(itemNo == 0){
            if(type==null||type.isEmpty()){
                mstore.collection("AllItems").get().addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Items items = doc.toObject(Items.class);
                            AllitemsList.add(items);
                            allItemsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
            if(type!=null&&type.equalsIgnoreCase("electronics")){
                mstore.collection("AllItems").whereEqualTo("type","Electronics").get().addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Items items = doc.toObject(Items.class);
                            AllitemsList.add(items);
                            allItemsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
            if(type!=null && type.equalsIgnoreCase("clothing")){
                mstore.collection("AllItems").whereEqualTo("type","Clothing").get().addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Items items = doc.toObject(Items.class);
                            AllitemsList.add(items);
                            allItemsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
            if(type!=null && type.equalsIgnoreCase("stationary")){
                mstore.collection("AllItems").whereEqualTo("type","Stationary").get().addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Items items = doc.toObject(Items.class);
                            AllitemsList.add(items);
                            allItemsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
            if(type!=null && type.equalsIgnoreCase("daily needs")){
                mstore.collection("AllItems").whereEqualTo("type","Daily needs").get().addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Items items = doc.toObject(Items.class);
                            AllitemsList.add(items);
                            allItemsAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }else if(itemNo==1){
            mstore.collection("User").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).collection("Orders")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc:task.getResult().getDocuments()){
                                String s = (String) doc.get("name");
                                ordersList.add(s);
                            }
                            for(int i = 0; i<ordersList.size(); i++){
                                mstore.collection("AllItems")
                                        .whereEqualTo("name",ordersList.get(i)).get().addOnCompleteListener((t) -> {
                                    if(t.isSuccessful()){
                                        for(DocumentSnapshot doc:t.getResult().getDocuments()){
                                            Items items = doc.toObject(Items.class);
                                            AllitemsList.add(items);
                                            allItemsAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                        }
                }
            }
            });
        }
        else if(itemNo==2){
            mstore.collection("User").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).collection("Sold")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> t) {
                    if(t.isSuccessful()){
                        for(DocumentSnapshot doc:t.getResult().getDocuments()){
                            Items items = doc.toObject(Items.class);
                            AllitemsList.add(items);
                            allItemsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }





    }
}