package com.example.vitstop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    TextView mTotal;
    TextView FinalAmount;
    Button payBtn;
    Button newlogout;
    double amount = 2.0;
    String name = "";
    String img_url = "";
    FirebaseFirestore mstore;
    FirebaseAuth  mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        amount = getIntent().getDoubleExtra("amount",0.0);
        amount = getIntent().getDoubleExtra("amount",2.0);
        img_url = getIntent().getStringExtra("img_url");
        name = getIntent().getStringExtra("name");
        mstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_payment);

        FinalAmount = findViewById(R.id.total_amount);
        mTotal = findViewById(R.id.subtotal);
        payBtn = findViewById(R.id.checkout);
        mTotal.setText("₹ "+amount+"");
        FinalAmount.setText("₹ "+amount+"");

        Checkout.preload(getApplicationContext());
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

    }
    public void startPayment() {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_6LyZWl6Ya2pblC");
        /**
         * Set your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = PaymentActivity.this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "VIT-STOP");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            amount = amount*100;
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.email", "samyakjain0826@gmail.com");
            options.put("prefill.contact","7689093370");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Payment Successful", Toast.LENGTH_SHORT).show();
        Map<String,Object> ItemMap = new HashMap<>();
        ItemMap.put("name", name);
        ItemMap.put("img_url",img_url);
        ItemMap.put("payment_id",s);
        mstore.collection("User").document(mAuth.getCurrentUser().getUid())
                .collection("Orders").add(ItemMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(Task<DocumentReference> task) {
                Intent intent = new Intent(PaymentActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        //Toast.makeText(this,""+s, Toast.LENGTH_SHORT).show();
        amount = amount/100;
    }
}