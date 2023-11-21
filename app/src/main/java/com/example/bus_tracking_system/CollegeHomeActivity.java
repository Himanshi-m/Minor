package com.example.bus_tracking_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.bus_tracking_system.addmodule.AddStudentActivity;
import com.example.bus_tracking_system.addmodule.AddBusActivity;
import com.example.bus_tracking_system.addmodule.AddBusAttendantActivity;

import com.example.bus_tracking_system.viewmodule.ViewBusActivity;
import com.example.bus_tracking_system.viewmodule.ViewBusAttendantActivity;
import com.example.bus_tracking_system.viewmodule.ViewStudentActivity;
import com.google.firebase.auth.FirebaseAuth;

public class CollegeHomeActivity extends AppCompatActivity {

    Button addStudent;
    Button viewStudent;
    Button addBus;
    Button viewBus;
    Button addBusAttendant;
    Button viewBusAttendant;
    Button signOut;

    String collegeId;

    private static final String TAG = "CollegeHomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_home_activity);

        addStudent = findViewById(R.id.btnAddStudent);
        viewStudent = findViewById(R.id.btnViewStudent);

        addBus = findViewById(R.id.btnAddBus);
        viewBus = findViewById(R.id.btnViewBus);

        addBusAttendant = findViewById(R.id.btnAddBusAttendant);
        viewBusAttendant = findViewById(R.id.btnViewBusAttendant);

        signOut = findViewById(R.id.logout);


        Intent intent = getIntent();
        collegeId = intent.getStringExtra("enteredCollegeId");
        Log.d(TAG, "College id through intent is : " + collegeId);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CollegeHomeActivity.this, LoginActivity.class));
                finish();
            }
        });

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAddStudentActivity = new Intent(CollegeHomeActivity.this, AddStudentActivity.class);
                intentToAddStudentActivity.putExtra("userType", "Student");
                intentToAddStudentActivity.putExtra("collegeId", collegeId);
                startActivity(intentToAddStudentActivity);
            }
        });

        viewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToViewStudent = new Intent(CollegeHomeActivity.this, ViewStudentActivity.class);
                intentToViewStudent.putExtra("collegeId", collegeId);
//                intentToViewStudent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentToViewStudent);
            }
        });

        addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAddBusActivity = new Intent(CollegeHomeActivity.this, AddBusActivity.class);
                intentToAddBusActivity.putExtra("collegeId", collegeId);
                intentToAddBusActivity.putExtra("userType", "College");
                intentToAddBusActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentToAddBusActivity);
            }
        });

        viewBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToViewBus = new Intent(CollegeHomeActivity.this, ViewBusActivity.class);
                intentToViewBus.putExtra("collegeId", collegeId);
//                intentToViewStudent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentToViewBus);
            }
        });

        addBusAttendant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAddBusAttendantActivity = new Intent(CollegeHomeActivity.this, AddBusAttendantActivity.class);
                intentToAddBusAttendantActivity.putExtra("collegeId", collegeId);
                intentToAddBusAttendantActivity.putExtra("userType", "BusAttendant");
                intentToAddBusAttendantActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentToAddBusAttendantActivity);
            }
        });

        viewBusAttendant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToViewBusAttendant = new Intent(CollegeHomeActivity.this, ViewBusAttendantActivity.class);
                intentToViewBusAttendant.putExtra("collegeId", collegeId);
//                intentToViewStudent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentToViewBusAttendant);
            }
        });

    }
}