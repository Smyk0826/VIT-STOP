package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.vitstop.Features.Items;
import com.example.vitstop.adapter.AllItemsAdapter;
import com.example.vitstop.fragments.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.vitstop.R.menu.main_menu;

public class HomeActivity extends AppCompatActivity {
    Fragment homeFragment;
    private Toolbar actionBar;
    private FirebaseAuth mAuth;
    private EditText searchText;
    private FirebaseFirestore mstore;
    private List<Items> resultList;
    private RecyclerView resultRecyclerView;
    private AllItemsAdapter resultAdapter;
    private ConstraintLayout home_page;
    private FrameLayout frontPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        home_page = (ConstraintLayout) findViewById(R.id.home_page);
        frontPage = (FrameLayout)findViewById(R.id.frontPage);
        homeFragment =  new Home();
        loadFragment(homeFragment);
        mAuth = FirebaseAuth.getInstance();
        actionBar = findViewById(R.id.Home_toolbar);
        setSupportActionBar(actionBar);
        actionBar.showOverflowMenu();
        mstore = FirebaseFirestore.getInstance();
        searchText = findViewById(R.id.search_text);
        resultList = new ArrayList<>();
        resultRecyclerView = findViewById(R.id.search_results);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        resultAdapter = new AllItemsAdapter(this,resultList);
        resultRecyclerView.setAdapter(resultAdapter);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    resultList.clear();
                    resultAdapter.notifyDataSetChanged();
                    home_page.addView(frontPage);
                }
                else{
                    home_page.removeView(frontPage);
                    searchItem(editable.toString());
                }

            }
        });
        //actionBar.showOverflowMenu();
    }

    private void searchItem(String text) {
        if(!text.isEmpty()){
            mstore.collection("AllItems").whereEqualTo("name",text).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if(task.isSuccessful()&&task.getResult()!=null){
                                for(DocumentSnapshot doc:task.getResult().getDocuments()){
                                    Items items = doc.toObject(Items.class);
                                    resultList.add(items);
                                    resultAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });

        }

    }

    public void LogOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, MainActivity3.class);
        startActivity(intent);
        finish();
    }
    private void loadFragment(Fragment fragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frontPage, homeFragment);
            transaction.addToBackStack(null);
            transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.log_out_btn){
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class );
            startActivity(intent);
            finish();
        }
        return true;
    }
}