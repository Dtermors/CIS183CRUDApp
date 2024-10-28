package com.example.cis183crudapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentListAdapter extends ArrayAdapter<Student>
{
    private Context context;
    private List<Student> students;

    public StudentListAdapter(Context context, List<Student> students) {
        super(context, R.layout.studentitem, students);
        this.context = context;
        this.students = students;
}

    @Override
    public View getView(int position, View datastore, ViewGroup parent) {
        if (datastore == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            datastore = inflater.inflate(R.layout.studentitem, parent, false);
        }

        TextView tv_j_fnameitem = datastore.findViewById(R.id.tv_v_fnameitem);
        TextView tv_j_lnameitem = datastore.findViewById(R.id.tv_v_lnameitem);
        TextView tv_j_username = datastore.findViewById(R.id.tv_v_unameitem);

        Student student = students.get(position);
        tv_j_fnameitem.setText(student.getFname());
        tv_j_lnameitem.setText(student.getLname());
        tv_j_username.setText(student.getUname());

        return datastore;
    }
}
