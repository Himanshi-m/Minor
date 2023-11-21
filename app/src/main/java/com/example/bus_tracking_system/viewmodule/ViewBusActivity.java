package com.example.bus_tracking_system.viewmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.bus_tracking_system.Model.BusModel;
import com.example.bus_tracking_system.R;
import com.example.bus_tracking_system.adapter.ViewBusAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewBusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<BusModel> busData;
    FirebaseFirestore mFirebaseFirestore;
    ViewBusAdapter viewBusAdapter;

    String collegeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus);

        recyclerView = findViewById(R.id.viewBusRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        busData = new ArrayList<>();
        viewBusAdapter = new ViewBusAdapter(busData, getApplicationContext());
        recyclerView.setAdapter(viewBusAdapter);

        Intent intent = getIntent();
        collegeId = intent.getStringExtra("collegeId");

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mFirebaseFirestore.collection("Colleges")
                .document(collegeId)
                .collection("Buses")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            BusModel busModel = d.toObject(BusModel.class);
                            busData.add(busModel);
                            Log.d("VIEWBUSACTIVITY", busModel.getBusNumber());
                        }
                        viewBusAdapter.notifyDataSetChanged();
                    }
                });

    }
}