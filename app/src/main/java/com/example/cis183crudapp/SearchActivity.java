package com.example.cis183crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText et_j_Search;
    private ListView lv_j_Students;
    private StudentListAdapter adapter;
    private List<Student> allStudents;
    private List<Student> filteredStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        et_j_Search = findViewById(R.id.et_v_search);
        lv_j_Students = findViewById(R.id.lv_v_students);

        //loads students
        DatabaseHelper sqLiteDatabase = new DatabaseHelper(this);
        allStudents = sqLiteDatabase.getAllStudents(); // Implement this method to fetch all students
        filteredStudents = new ArrayList<>(allStudents);

        adapter = new StudentListAdapter(this, filteredStudents);
        lv_j_Students.setAdapter(adapter);
        //activates when text is changed
        et_j_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterStudents(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        lv_j_Students.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student selectedStudent = (Student) parent.getItemAtPosition(position);
                Intent intent = new Intent(SearchActivity.this, Details.class);
                intent.putExtra("username", selectedStudent.getUname()); // Pass the username
                startActivity(intent);
            }
        });
    }

    private void filterStudents(String query) {
        filteredStudents.clear();
        for (Student student : allStudents) {
            if (student.getFname().toLowerCase().contains(query.toLowerCase()) ||
                    student.getLname().toLowerCase().contains(query.toLowerCase()) ||
                    student.getUname().toLowerCase().contains(query.toLowerCase()) ||
                    student.getMajor().toLowerCase().contains(query.toLowerCase()) ||
                    String.valueOf(student.getGpa()).contains(query)) {
                filteredStudents.add(student);
            }
        }
        adapter.notifyDataSetChanged();
    }

}