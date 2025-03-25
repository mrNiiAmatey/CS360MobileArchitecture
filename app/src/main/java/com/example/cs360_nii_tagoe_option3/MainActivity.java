package com.example.cs360_nii_tagoe_option3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBase db;
    RecyclerView rvWeights;
    WeightAdapter adapter;
    ArrayList<WeightHolder> weightItems;
    Button btnAddWeight;
    EditText etDate, etWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_database_info);

        db = new DataBase(this);
        rvWeights = findViewById(R.id.rvWeights);
        btnAddWeight = findViewById(R.id.btnAddWeight);
        etDate = findViewById(R.id.etDate);
        etWeight = findViewById(R.id.etWeight);

        weightItems = new ArrayList<>();
        loadWeights();

        // Initialize RecyclerView with a linear layout manager and adapter.
        adapter = new WeightAdapter(this, weightItems, db, this::refreshWeights);
        rvWeights.setLayoutManager(new LinearLayoutManager(this));
        rvWeights.setAdapter(adapter);

        // Add weight button logic.
        btnAddWeight.setOnClickListener(v -> {
            String date = etDate.getText().toString().trim();
            String weight = etWeight.getText().toString().trim();
            if(date.isEmpty() || weight.isEmpty()){
                Toast.makeText(MainActivity.this, "Please enter both date and weight", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean inserted = db.insertWeight(date, weight);
            if(inserted){
                Toast.makeText(MainActivity.this, "Weight added", Toast.LENGTH_SHORT).show();
                etDate.setText("");
                etWeight.setText("");
                refreshWeights();
            } else {
                Toast.makeText(MainActivity.this, "Error adding weight", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshWeights(){
        weightItems.clear();
        loadWeights();
        adapter.notifyDataSetChanged();
    }

    private void loadWeights(){
        Cursor cursor = db.getAllWeights();
        if(cursor != null && cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBase.COLUMN_WEIGHT_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_DATE));
                String weight = cursor.getString(cursor.getColumnIndexOrThrow(DataBase.COLUMN_WEIGHT_VALUE));
                weightItems.add(new WeightHolder(id, date, weight));
            } while(cursor.moveToNext());
            cursor.close();
        }
    }
}