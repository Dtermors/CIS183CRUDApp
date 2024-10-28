package com.example.cis183crudapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {
    TextView tv_j_nameD;
    TextView tv_j_unameD;
    TextView tv_j_emailD;
    TextView tv_j_ageD;
    TextView tv_j_gpaD;
    TextView tv_j_majorD;
    Button btn_j_home;
    Intent returnHome;
    Intent gotoedit;
    Button btn_j_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        tv_j_nameD = findViewById(R.id.tv_v_nameD);
        tv_j_unameD = findViewById(R.id.tv_v_unameD);
        tv_j_emailD = findViewById(R.id.tv_v_emailD);
        tv_j_ageD = findViewById(R.id.tv_v_ageD);
        tv_j_gpaD = findViewById(R.id.tv_v_gpaD);
        tv_j_majorD = findViewById(R.id.tv_v_majorD);
        btn_j_home = findViewById(R.id.Home);
        btn_j_edit = findViewById(R.id.edit);

        //gets username
        String username = getIntent().getStringExtra("username");

        btn_j_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHome = new Intent(Details.this, MainActivity.class);
                startActivity(returnHome);
            }
        });
        btn_j_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoedit = new Intent(Details.this, UpdateStudent.class);
                gotoedit.putExtra("username", username);
                startActivity(gotoedit);
            }
        });

        //load details
        loadDetails(username);
    }
    private void loadDetails (String username)
    {
        DatabaseHelper sqLiteDatabase = new DatabaseHelper(this);
        Student student = sqLiteDatabase.getStudentByUsername(username);
    // sets text
        if (student != null)
        {
            tv_j_nameD.setText(student.getFname() + " " + student.getLname());
            tv_j_unameD.setText(student.getUname());
            tv_j_emailD.setText(student.getEmail());
            tv_j_ageD.setText(String.valueOf(student.getAge()));
            tv_j_gpaD.setText(String.valueOf(student.getGpa()));
            tv_j_majorD.setText(student.getMajor());
        //in case student isnt found
        }else {
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
        }
    }
}