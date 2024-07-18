package com.example.sodapop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    private EditText inputBranch, inputBrand, inputQuantity;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        inputBranch = findViewById(R.id.branch);
        inputBrand = findViewById(R.id.brand);
        inputQuantity = findViewById(R.id.quantity);

        findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String branch = inputBranch.getText().toString().trim();
                String brand = inputBrand.getText().toString().trim();
                String quantity = inputQuantity.getText().toString().trim();

                if (TextUtils.isEmpty(branch) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(quantity)) {
                    Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> order = new HashMap<>();
                order.put("branch", branch);
                order.put("brand", brand);
                order.put("quantity", quantity);
                order.put("customerId", auth.getCurrentUser().getUid());

                db.collection("orders").add(order)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity3.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity3.this, "Order placement failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
