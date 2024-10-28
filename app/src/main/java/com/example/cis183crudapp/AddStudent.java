package com.example.cis183crudapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends AppCompatActivity {
    private EditText et_j_fname;
    private EditText et_j_lname;
    private EditText et_j_uname;
    private EditText et_j_email;
    private EditText et_j_age;
    private EditText et_j_gpa;
    private Spinner sp_j_major;
    private ArrayAdapter<String> majorAdapter;
    DatabaseHelper databaseHelper;
    Intent returnHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        // Setting variables
        et_j_fname = findViewById(R.id.et_v_fname);
        et_j_lname = findViewById(R.id.et_v_lname);
        et_j_uname = findViewById(R.id.et_v_uname);
        et_j_email = findViewById(R.id.et_v_email);
        et_j_age = findViewById(R.id.et_v_age);
        et_j_gpa = findViewById(R.id.et_v_gpa);
        sp_j_major = findViewById(R.id.sp_v_major);
        Button btn_j_addback = findViewById(R.id.btn_v_addback);
        Button btn_j_AShome = findViewById(R.id.homeButton);
        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Add button code
        btn_j_addback.setOnClickListener(view -> addStudent());
        btn_j_AShome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHome = new Intent(AddStudent.this, MainActivity.class);
                startActivity(returnHome);
            }
        });
        // Fills spinner
        loadMajors();
    }

    private void addStudent() {
        String fname = et_j_fname.getText().toString();
        String lname = et_j_lname.getText().toString();
        String uname = et_j_uname.getText().toString();
        String email = et_j_email.getText().toString();
        int age = Integer.parseInt(et_j_age.getText().toString());
        float gpa = Float.parseFloat(et_j_gpa.getText().toString());
        String major = sp_j_major.getSelectedItem().toString(); // Corrected this line

        if (databaseHelper.addnewStudent(new Student(fname, lname, uname, email, age, gpa, major))) {
            Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
            clearText();
        } else {
            Toast.makeText(this, "Error adding student", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearText() {
        et_j_fname.setText("");
        et_j_lname.setText("");
        et_j_uname.setText("");
        et_j_email.setText("");
        et_j_age.setText("");
        et_j_gpa.setText("");
    }

    private void loadMajors() {
        List<String> majorList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase(); // Use the initialized databaseHelper
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + DatabaseHelper.col_major_name + " FROM " + DatabaseHelper.tableofMajors, null);

        if (cursor.moveToFirst()) {
            do {
                majorList.add(cursor.getString(0)); // Add major names to the list
            } while (cursor.moveToNext());
        }
        cursor.close();

        majorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorList);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_j_major.setAdapter(majorAdapter); // Set the adapter to the spinner
    }
}