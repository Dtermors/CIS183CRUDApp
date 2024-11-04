package com.example.cis183crudapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateStudent extends AppCompatActivity {
    private EditText et_j_fname;
    private EditText et_j_lname;
    private EditText et_j_email;
    private EditText et_j_age;
    private EditText et_j_gpa;
    private Spinner sp_j_major;
    private String username;
    Button btn_j_update;
    DatabaseHelper databaseHelper;
    ArrayAdapter<String> majorAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_student);
        et_j_fname = findViewById(R.id.et_fname);
        et_j_lname = findViewById(R.id.et_lname);
        et_j_email = findViewById(R.id.et_email);
        et_j_age = findViewById(R.id.et_age);
        et_j_gpa = findViewById(R.id.et_gpa);
        sp_j_major = findViewById(R.id.sp_major);
        btn_j_update = findViewById(R.id.btn_update);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Gets username from intent
        username = getIntent().getStringExtra("username");
        if (username == null) {
            Toast.makeText(this, "Error: Username not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCurrentStudentDetails(username);
        loadMajors(); // Load majors into the spinner
        btn_j_update.setOnClickListener(view -> updateStudent());
    }

    private void loadCurrentStudentDetails(String username) {
        DatabaseHelper db = new DatabaseHelper(this);
        Student student = db.getStudentByUsername(username);

        if (student != null) {
            et_j_fname.setText(student.getFname());
            et_j_lname.setText(student.getLname());
            et_j_email.setText(student.getEmail());
            et_j_age.setText(String.valueOf(student.getAge()));
            et_j_gpa.setText(String.valueOf(student.getGpa()));

            // Load majors first
            loadMajors();

            // Set the selected major
            setSelectedMajor(student.getMajor());
        } else {
            // Handle case when student is not found
            Toast.makeText(this, "Student Not Found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setSelectedMajor(String major) {
        if (majorAdapter != null) {
            int position = majorAdapter.getPosition(major);
            if (position >= 0) {
                sp_j_major.setSelection(position);
            }
        }
    }

    private void loadMajors() {
        List<String> majorList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT " + DatabaseHelper.col_major_name + " FROM " + DatabaseHelper.tableofMajors, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    majorList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        majorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorList);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_j_major.setAdapter(majorAdapter);
    }

    private void updateStudent() {
        String fname = et_j_fname.getText().toString();
        String lname = et_j_lname.getText().toString();
        String email = et_j_email.getText().toString();
        int age = Integer.parseInt(et_j_age.getText().toString());
        float gpa = Float.parseFloat(et_j_gpa.getText().toString());
        String major = sp_j_major.getSelectedItem().toString();

        // Check for empty fields, if needed
        if (fname.isEmpty() || lname.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Student updatedStudent = new Student(fname, lname, username, email, age, gpa, major);
        if (databaseHelper.updateStudent(updatedStudent)) {
            Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();

            // Create an intent to go back to the details page
            Intent intent = new Intent(this, Details.class);
            intent.putExtra("username", username); // Send the updated username
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error updating student", Toast.LENGTH_SHORT).show();
        }
    }
}