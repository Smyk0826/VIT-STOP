package com.example.vitstop.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vitstop.AllItems;
import com.example.vitstop.Features.CategoryFragment;
import com.example.vitstop.Features.Popular;
import com.example.vitstop.R;
import com.example.vitstop.adapter.CategoryAdapter;
import com.example.vitstop.adapter.PopularAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    private FirebaseFirestore db;
    //category
    private List<CategoryFragment> listCategory;
    private CategoryAdapter mCategoryAdapter;
    private RecyclerView categoryRecycler;

    private TextView cat_see;
    private TextView popular_see;

    //populars
    private List<Popular> listPopular;
    private PopularAdapter mPopularAdapter;
    private RecyclerView PopularRecycler;


    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();
        cat_see = view.findViewById(R.id.cat_see);
        popular_see = view.findViewById(R.id.popular_see);
        //category
        categoryRecycler = view.findViewById(R.id.recycler_category);
        listCategory = new ArrayList<>();
        mCategoryAdapter = new CategoryAdapter(getContext(),listCategory);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
        categoryRecycler.setAdapter(mCategoryAdapter);

        //Popular
        PopularRecycler = view.findViewById(R.id.popular_recycler);
        listPopular = new ArrayList<>();
        mPopularAdapter = new PopularAdapter(getContext(),listPopular);
        PopularRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        PopularRecycler.setAdapter(mPopularAdapter);


        db.collection("CategoryFragment")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryFragment categoryFragment = document.toObject(CategoryFragment.class);
                                listCategory.add(categoryFragment);
                                mCategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("Popular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Popular popular = document.toObject(Popular.class);
                                listPopular.add(popular);
                                mPopularAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
                 popular_see.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(getContext(), AllItems.class);
                        startActivity(intent);
                    }
                });
                cat_see.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(getContext(), AllItems.class);
                        startActivity(intent);
                    }
                });

        return view;
    }
}