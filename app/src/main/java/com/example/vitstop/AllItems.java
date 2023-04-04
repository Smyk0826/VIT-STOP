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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllItems extends AppCompatActivity {
    private FirebaseFirestore mstore;
    List<Items> AllitemsList;
    private RecyclerView AllItemsRecyclerView; //all items recycler view
    private AllItemsAdapter allItemsAdapter;
    private Toolbar itemToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);
        String type = getIntent().getStringExtra("type");

        mstore = FirebaseFirestore.getInstance();
        AllitemsList = new ArrayList<>();
        itemToolbar = findViewById(R.id.item_toolbar3);
        setSupportActionBar(itemToolbar);
        getSupportActionBar().setTitle("Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AllItemsRecyclerView = findViewById(R.id.AllItems_Recycler);
        AllItemsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        allItemsAdapter = new AllItemsAdapter(this,AllitemsList);
        AllItemsRecyclerView.setAdapter(allItemsAdapter);
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




    }
}