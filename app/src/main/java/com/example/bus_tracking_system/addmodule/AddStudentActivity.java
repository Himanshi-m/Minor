package com.example.bus_tracking_system.addmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bus_tracking_system.Model.StudentModel;
import com.example.bus_tracking_system.Model.UsersModel;
import com.example.bus_tracking_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class AddStudentActivity extends AppCompatActivity {

    EditText studentName;
    EditText studentRollNumber;
    EditText studentAddress;
    EditText studentCity;
    EditText studentState;
    EditText studentCountry;
    EditText studentPicDropLoc;
    EditText studentContactNumber;
    EditText studentEmail;
    EditText studentEmailPassword;
    EditText studentBusRouteNumber;
    EditText studentBusNumber;
    Button submitStudentDetails;
    ProgressBar progressBar;

    String userType;
    String collegeId;

    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirebaseFirestore;
    CollectionReference mCollegeCollection;
    CollectionReference mUserCollectionRef;
    CollectionReference mStudentDocRef;

    StudentModel studentModelForStudentDatabase;
    UsersModel studentModelForUserDatabase;

    private static final String TAG = "AddStudentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        studentName = findViewById(R.id.studentName);
        studentRollNumber = findViewById(R.id.studentRollNumber);
        studentAddress = findViewById(R.id.studentAddress);
        studentCity = findViewById(R.id.studentCity);
        studentState = findViewById(R.id.studentState);
        studentCountry = findViewById(R.id.studentCountry);
        studentPicDropLoc = findViewById(R.id.studentPicDropLoc);
        studentContactNumber = findViewById(R.id.studentContactNumber);
        studentEmail = findViewById(R.id.studentEmail);
        studentEmailPassword = findViewById(R.id.studentPassword);
        studentBusRouteNumber = findViewById(R.id.studentBusRouteNumber);
        studentBusNumber = findViewById(R.id.studentBusNumber);
        progressBar = findViewById(R.id.progressBarForAddStudent);

        submitStudentDetails = findViewById(R.id.btnSubmitStudentDetails);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollegeCollection = mFirebaseFirestore.collection("Colleges");
        mUserCollectionRef = mFirebaseFirestore.collection("Users");

        Intent intentToGetUserType = getIntent();
        userType = intentToGetUserType.getStringExtra("userType");

        Intent intentToGetSchoolId = getIntent();
        collegeId = intentToGetSchoolId.getStringExtra("collegeId");

        Log.d(TAG, "UserType and CollegeId Through intent is : " + userType + " " + collegeId);

        submitStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                String stuEmail = studentEmail.getText().toString().trim();
                String studentPass = studentEmailPassword.getText().toString();

                String name = studentName.getText().toString();
                String rollNo = studentRollNumber.getText().toString();
                String address = studentAddress.getText().toString();
                String city = studentCity.getText().toString();
                String state = studentState.getText().toString();
                String country = studentCountry.getText().toString();
                String picDrop = studentPicDropLoc.getText().toString();
                String ContactNum = studentContactNumber.getText().toString();
                String busRouteNum = studentBusRouteNumber.getText().toString();
                String busNumber = studentBusNumber.getText().toString();

                studentModelForStudentDatabase =
                        new StudentModel(name, rollNo, address, city, state, country, picDrop,
                                ContactNum, stuEmail, busRouteNum, busNumber);

                studentModelForUserDatabase =
                        new UsersModel(stuEmail, userType, collegeId);

                addUserForAuthentication(stuEmail, studentPass);
            }
        });
    }

    private void addUserForAuthentication(String studentEmail, String studentPass) {
        mFirebaseAuth.createUserWithEmailAndPassword(studentEmail, studentPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "Sign in successful");
                            Toast.makeText(AddStudentActivity.this, "signInWithEmailPass : Successful", Toast.LENGTH_SHORT).show();
                            addUserInfoToFirebaseUsersCollection(studentModelForUserDatabase);
                            addUserInfoToFirestoreStudentCollection(studentModelForStudentDatabase);
                            clearAllFields();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.w(TAG, "SignInWithEmailPass : Failure");
                            Toast.makeText(AddStudentActivity.this, "signInWithEmailPass : Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e(TAG, e.getMessage());
                Toast.makeText(AddStudentActivity.this, "Failed to authenticate user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserInfoToFirebaseUsersCollection(UsersModel studentModelForUserDatabase) {
        mUserCollectionRef
                .document(studentModelForUserDatabase.getEmailId())
                .set(studentModelForUserDatabase)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DATA Added to users collection");
                        Toast.makeText(AddStudentActivity.this, "Data added to users collection", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG, "Failed to add data to Users Collection : " + e.getMessage());
            }
        });
    }

    private void addUserInfoToFirestoreStudentCollection(StudentModel studentModelForStudentDatabase) {
        mStudentDocRef = mCollegeCollection.document(collegeId).collection("Students");
        mStudentDocRef
                .document(studentModelForStudentDatabase.getRollNo())
                .set(studentModelForStudentDatabase)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Added data at College Successfully");
                        Toast.makeText(AddStudentActivity.this, "Added data to college database", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG, "Failed to add data in College : " + e.getMessage());
            }
        });
    }

    private void clearAllFields() {
        studentName.setText("");
        studentRollNumber.setText("");
        studentAddress.setText("");
        studentCity.setText("");
        studentState.setText("");
        studentCountry.setText("");
        studentPicDropLoc.setText("");
        studentContactNumber.setText("");
        studentEmail.setText("");
        studentEmailPassword.setText("");
        studentBusRouteNumber.setText("");
        studentBusNumber.setText("");

        studentModelForStudentDatabase = new StudentModel();
        studentModelForUserDatabase = new UsersModel();
    }

}