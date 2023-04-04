package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vitstop.Features.CategoryFragment;
import com.example.vitstop.Features.Items;
import com.example.vitstop.Features.Popular;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {
    private ImageView mImage;
    private TextView mItemName;
    private TextView mPrice;
    private Button mItemRating;
    private TextView mItemRatingDes;
    private TextView mItemDesc;
    private Button mAddtoCart;
    private Button mBuyBut;
    private Toolbar mToolbar;
    private FirebaseFirestore mstore;
    Items items = null;
    Popular popular = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mstore = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_detail);
        mToolbar = findViewById(R.id.Detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Popular popular = (Popular) getIntent().getSerializableExtra("detail");
        mImage = findViewById(R.id.item_img);
        mItemName = findViewById(R.id.item_name);
        mPrice = findViewById(R.id.item_price);
        mItemRating = findViewById(R.id.item_rating);
        mItemRatingDes = findViewById(R.id.item_ratingDes);
        mItemDesc = findViewById(R.id.item_des);
        mAddtoCart = findViewById(R.id.item_addCart);
        mBuyBut = findViewById(R.id.item_buy);
        final Object obj = getIntent().getSerializableExtra("detail");
//        Popular popular = null;
//        CategoryFragment category = null;
        if(obj instanceof Popular){
            popular = (Popular) obj;
        }
        else if(obj instanceof Items){
            items = (Items) obj;
        }
        if(popular!=null) {
            Glide.with(getApplicationContext()).load(popular.getImg_url()).into(mImage);
            mItemName.setText(popular.getName());
            mPrice.setText((int) popular.getPrice() + "₹");
            mItemRating.setText(popular.getRating() + "");
            if (popular.getRating() > 3) {
                mItemRatingDes.setText("Very Good");
            } else {
                mItemRatingDes.setText("Bad");
            }
            mItemDesc.setText(popular.getDescription());
        }
        if(items!=null) {
            Glide.with(getApplicationContext()).load(items.getImg_url()).into(mImage);
            mItemName.setText(items.getName());
            mPrice.setText((int) items.getPrice() + "₹");
            mItemRating.setText(items.getRating() + "");
            if (items.getRating() > 3) {
                mItemRatingDes.setText("Very Good");
            } else {
                mItemRatingDes.setText("Bad");
            }
            mItemDesc.setText(items.getDescription());
        }

        mAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBuyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,AddressActivity.class);
                if(popular!=null){
                    intent.putExtra("item",popular);
                }
                if(items!=null){
                    intent.putExtra("item",items);
                }

                startActivity(intent);
            }
        });
    }
}