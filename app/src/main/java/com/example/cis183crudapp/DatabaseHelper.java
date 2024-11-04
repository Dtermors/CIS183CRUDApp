package com.example.cis183crudapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DatabaseName = "student_manager.db";
    private static final int DatabaseVersion = 1;

    //table
    private static final String tableOfStudents = "students";
    private static final String col_fname = "first_name";
    private static final String col_lname = "last_name";
    private static final String col_uname = "username";//key
    private static final String col_email = "email";
    private static final String col_age = "age";
    private static final String col_gpa = "gpa";
    private static final String col_major = "major";

    //majors table
    public static final String tableofMajors = "majors";
    public static final String col_major_id = "major_id"; // key
    public static final String col_major_name = "major_name";
    public static final String col_major_prefix = "major_prefix";

    public DatabaseHelper(Context context)
    {
        super(context, DatabaseName, null, DatabaseVersion);
    }
    //students and majors tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create table " + tableOfStudents + " (" + col_fname + " text, " + col_lname + " text, " + col_uname + " text primary key, " + col_email + " text, " + col_age + " integer, " + col_gpa + " real, " + col_major + " text)";
        sqLiteDatabase.execSQL(createTable);
        //major table
        String createMTable = "create table " + tableofMajors + " (" + col_major_id + " integer primary key, " + col_major_name + " text, " + col_major_prefix + " text unique)";
        sqLiteDatabase.execSQL(createMTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + tableOfStudents);
        onCreate(sqLiteDatabase);
    }

    public boolean addnewStudent(Student student) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_fname, student.getFname());
        values.put(col_lname, student.getLname());
        values.put(col_uname, student.getUname());
        values.put(col_email, student.getEmail());
        values.put(col_age, student.getAge());
        values.put(col_gpa, student.getGpa());
        values.put(col_major, student.getMajor());

        long result = sqLiteDatabase.insert(tableOfStudents, null, values);
        sqLiteDatabase.close();
        return result != -1;
    }
    //gets details for list on homepage
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableOfStudents, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getFloat(5),
                        cursor.getString(6)
                );
                studentList.add(student);
            }while (cursor.moveToNext()) ;
        }
        cursor.close();
        sqLiteDatabase.close();
        return studentList;
    }

    public void deleteStudent(String uname) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(tableOfStudents, col_uname + " = ?", new String[]{uname});
        sqLiteDatabase.close();
    }
    // gets the username for the displaypage
    public Student getStudentByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableOfStudents, null, col_uname + "=?", new String[]{username}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Retrieve student details
                @SuppressLint("Range") String fname = cursor.getString(cursor.getColumnIndex(col_fname));
                @SuppressLint("Range") String lname = cursor.getString(cursor.getColumnIndex(col_lname));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(col_email));
                @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex(col_age));
                @SuppressLint("Range") float gpa = cursor.getFloat(cursor.getColumnIndex(col_gpa));
                @SuppressLint("Range") String major = cursor.getString(cursor.getColumnIndex(col_major));
                cursor.close();
                return new Student(fname, lname, username, email, age, gpa, major);
            }
            cursor.close();
        }
        return null; // Student not found
    }

    public boolean addMajor(Majors majors){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_major_name, majors.getMajorName());
        values.put(col_major_id, majors.getMajorId());
        values.put(col_major_prefix, majors.getMajorPrefix());

        long result = sqLiteDatabase.insert(tableofMajors, null, values);
        sqLiteDatabase.close();
        return result != -1;
    }
    public List<Majors> getAllMajors(){
        List<Majors> majorsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableofMajors, null);

        if (cursor.moveToFirst())
        {
            do {
                Majors majors = new Majors(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                majorsList.add(majors);
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return majorsList;
    }

    public boolean majorExists(String majorName){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(tableofMajors, null, col_major_name + " = ?", new String[]{majorName}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        sqLiteDatabase.close();
        return exists;
    }
    public boolean updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_fname, student.getFname());
        values.put(col_lname, student.getLname());
        values.put(col_email, student.getEmail());
        values.put(col_age, student.getAge());
        values.put(col_gpa, student.getGpa());
        values.put(col_major, student.getMajor());

        // Update the row where the username matches
        int result = db.update(tableOfStudents, values, col_uname + "=?", new String[]{student.getUname()});
        return result > 0; // Returns true if at least one row was updated
    }


}
