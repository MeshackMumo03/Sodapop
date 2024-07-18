package com.example.sodapop;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sodapop.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Drink Spinner
        ArrayAdapter<CharSequence> drinkAdapter = ArrayAdapter.createFromResource(this,
                R.array.drinks_array, android.R.layout.simple_spinner_item);
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.drinkSpinner.setAdapter(drinkAdapter);

        // Branch Spinner
        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(this,
                R.array.branches_array, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.BranchSpinner.setAdapter(branchAdapter);

        binding.orderButton.setOnClickListener(v -> placeOrder());
    }

    private void placeOrder() {
        String name = binding.editTextName.getText().toString().trim();
        String amount = binding.editTextAmount.getText().toString().trim();
        String drink = binding.drinkSpinner.getSelectedItem().toString();
        String branch = binding.BranchSpinner.getSelectedItem().toString();

        if (name.isEmpty() || amount.isEmpty() || drink.isEmpty() || branch.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save order to Firebase
        String orderId = mDatabase.child("orders").push().getKey();
        Order order = new Order(name, drink, branch, amount);
        if (orderId != null) {
            mDatabase.child("orders").child(orderId).setValue(order)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}