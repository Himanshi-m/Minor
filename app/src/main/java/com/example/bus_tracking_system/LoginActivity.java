package com.example.bus_tracking_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    //XML tags references
    EditText userEmail;
    EditText userPass;
    Button userLoginBtn;
    ProgressBar progressBar;

    //Debug variable
    private static final String TAG = "LOGIN ACTIVITY";

    //Firebase Authentication Reference
    FirebaseAuth mFirebaseAuth;

    //Firebase Firestore references
    FirebaseFirestore mFirebaseFirestore;
    CollectionReference mUserCollection;
    CollectionReference mCollegeCollection;
    DocumentReference mDocumentUserReference;

    //Edittext references to string typecasting
    String mUserEmail;
    String mUserPass;

    //String variable for getting user login type and corresponding school id from 'Users' collection
    String userType;
    String collegeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing XML tag id references
        userEmail = findViewById(R.id.inputEmail);
        userPass = findViewById(R.id.inputPassword);
        userLoginBtn = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        //FirebaseAuth initialization
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Firebase Firestore references initialization
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUserCollection = mFirebaseFirestore.collection("Users");
        mCollegeCollection = mFirebaseFirestore.collection("Colleges");

        //Set onClickListener on Login Button
        userLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting progress bar to VISIBLE when login button clicked
                progressBar.setVisibility(View.VISIBLE);

                Log.d(TAG, "In Login button onClickListener");

                //Casting EditText references to String
                mUserEmail = userEmail.getText().toString().trim();
                mUserPass = userPass.getText().toString();

                if (TextUtils.isEmpty(mUserEmail)) {
                    userEmail.setError("Email is required");
                }

                if (TextUtils.isEmpty(mUserPass)) {
                    userPass.setError("Password is Required.");
                    return;
                }

                if (mUserPass.length() < 6) {
                    userPass.setError("Password Must be >= 6 Characters");
                    return;
                }

                //Call to Login process
                loginCheck(mUserEmail, mUserPass);
            }
        });
    }

    private void loginCheck(String email, String pass) {

        mFirebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //After login, progress bar set to INVISIBLE
                        progressBar.setVisibility(View.INVISIBLE);

                        Log.d(TAG, "Checking user authentication");

                        //Login email check by Firestore Users Collection
                        mDocumentUserReference = mUserCollection.document(email);
                        mDocumentUserReference
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        Log.d(TAG, "Checking userType exits or not in Users collection");

                                        //If users email found then we fetch userType and schoolId
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot doc = task.getResult();
                                            if (doc.exists()) {
                                                userType = doc.getString("userType");
                                                collegeId = doc.getString("collegeId");
                                                Log.d(TAG, "User type is " + userType + " " + collegeId);

                                                /*After fetch userType and schoolId,
                                                 Send user to there respective activity
                                                 */
                                                showActivityAccordingToUserType();
                                            } else {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(LoginActivity.this, "User type not found", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "user email not found in Users collection");
                                            }
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Log.d(TAG, "Failed to get document reference data in Users collection");
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Invalid login details");
                Toast.makeText(LoginActivity.this, "Enter valid details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showActivityAccordingToUserType() {
//        mSchoolCollection
        Log.d(TAG, "School id is " + collegeId);
//        FirebaseFirestore.getInstance()
//                .collection("Schools")
        mCollegeCollection
                .document(collegeId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "Data is collegeId : " + documentSnapshot.getString("collegeID"));
                        if (Objects.equals(documentSnapshot.getString("collegeID"), collegeId)) {

                            if (userType.equals("College")) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Welcome " + userEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                                Intent collegeIntent = new Intent(LoginActivity.this, CollegeHomeActivity.class);
                                collegeIntent.putExtra("enteredCollegeId", collegeId);
//                              collegeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(collegeIntent);
                                finish();
                            }

                            if (userType.equals("BusAttendant")) {
                                Toast.makeText(LoginActivity.this, "Welcome " + userEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent busAttendantIntent = new Intent(LoginActivity.this, BusAttendantActivity.class);
                                busAttendantIntent.putExtra("enteredCollegeId", collegeId);
                                busAttendantIntent.putExtra("attendantsLoginId", mUserEmail);
                                startActivity(busAttendantIntent);
                                finish();
                            }

                            if (userType.equals("Student")) {
                                Toast.makeText(LoginActivity.this, "Welcome " + userEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent studentIntent = new Intent(LoginActivity.this, StudentActivity.class);
                                studentIntent.putExtra("parentEmail", mUserEmail);
                                studentIntent.putExtra("enteredCollegeId", collegeId);
                                startActivity(studentIntent);
                                finish();
                            }

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "College id is Invalid, unable to send you IN", Toast.LENGTH_SHORT).show();
                        }

//                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "Unable to find college id in College collection");
                        Toast.makeText(LoginActivity.this, "College Id Not Found", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}