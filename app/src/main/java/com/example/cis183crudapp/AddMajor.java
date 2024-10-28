package com.example.cis183crudapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddMajor extends AppCompatActivity {
    EditText et_j_mname;
    EditText et_j_mprefix;
    EditText et_j_mid;
    Button  btn_j_addmaj;
    Button btn_j_AMhome;
    DatabaseHelper databaseHelper;
    Intent returnHome;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_major);
        databaseHelper = new DatabaseHelper(this);
        et_j_mname = findViewById(R.id.et_v_mname);
        et_j_mprefix = findViewById(R.id.et_v_Prefix);
        et_j_mid = findViewById(R.id.et_v_mid);
        btn_j_addmaj = findViewById(R.id.btn_v_addmajor);
        btn_j_AMhome = findViewById(R.id.backtohome);

        btn_j_addmaj.setOnClickListener(view -> addMajors());

        btn_j_AMhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHome = new Intent(AddMajor.this, MainActivity.class);
                startActivity(returnHome);
            }
        });

    }
    //function for adding new majors
    private void addMajors() {
        String majorName = et_j_mname.getText().toString().trim();
        String majorPrefix = et_j_mprefix.getText().toString().trim();
        int majorID = Integer.parseInt(et_j_mid.getText().toString());

        if (majorName.isEmpty() || majorPrefix.isEmpty()) {
            Toast.makeText(this, "Please enter both name and prefix", Toast.LENGTH_SHORT).show();
            return;
        }
        if (databaseHelper.majorExists(majorName)) {
            Toast.makeText(this, "Major already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        Majors newMajor = new Majors(majorID, majorName, majorPrefix);
        if (databaseHelper.addMajor(newMajor)) {
            Toast.makeText(this, "Major added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Error adding major", Toast.LENGTH_SHORT).show();
        }
    }
    private void clearFields() {
        et_j_mname.setText("");
        et_j_mprefix.setText("");
        et_j_mid.setText("");
    }
}