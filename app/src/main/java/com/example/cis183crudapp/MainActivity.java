package com.example.cis183crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //variables
    Button btn_j_add;
    Button btn_j_addmajors;
    Button btn_j_search;
    ListView lv_j_studentlist;
    StudentListAdapter StudentListAdapter;
    Intent intent_j_add;
    Intent intent_j_addmajors;
    Intent intent_j_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn_j_add = findViewById(R.id.btn_v_add);
        btn_j_addmajors = findViewById(R.id.btn_v_addmajor);
        btn_j_search = findViewById(R.id.btn_v_search);
        lv_j_studentlist = findViewById(R.id.lv_v_studentlist);

        loadStudentTable();

        btn_j_addmajors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_j_addmajors = new Intent(MainActivity.this, AddMajor.class);
                startActivity(intent_j_addmajors);
            }
        });

        btn_j_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_j_add  = new Intent(MainActivity.this, AddStudent.class);
                startActivity(intent_j_add);
            }
        });
        btn_j_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_j_search = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent_j_search);
            }
        });

    }
    private void loadStudentTable()
    {
        DatabaseHelper sqLiteDatabase = new DatabaseHelper(this);
        List<Student> students = sqLiteDatabase.getAllStudents();
        StudentListAdapter = new StudentListAdapter(this, students);
        lv_j_studentlist.setAdapter(StudentListAdapter);

        //clicking on sudents for details
        lv_j_studentlist.setOnItemClickListener((parent, view, position, id) ->{
            Student selectedStudent = (Student) StudentListAdapter.getItem(position);
            Intent intent = new Intent(MainActivity.this, Details.class);
            intent.putExtra("username", selectedStudent.getUname());
            startActivity(intent);
        });

        //long click to delete students
        lv_j_studentlist.setOnItemLongClickListener((parent, view, position, id) -> {
            Student selectedStudent = (Student) StudentListAdapter.getItem(position);
            sqLiteDatabase.deleteStudent(selectedStudent.getUname());
            // Reloads the list after deletion
            loadStudentTable();
            return true;
        });
    }
}