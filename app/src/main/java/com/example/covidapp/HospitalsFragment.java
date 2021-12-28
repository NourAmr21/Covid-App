package com.example.covidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HospitalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HospitalsFragment extends Fragment {
    Button hosp;
    RecyclerView recyclerview;
    DatabaseReference database;
    MyHospitals myAdapter;
    ArrayList<Hospital> arrayList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HospitalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Hospitals.
     */
    // TODO: Rename and change types and number of parameters
    public static HospitalsFragment newInstance(String param1, String param2) {
        HospitalsFragment fragment = new HospitalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(MainActivity.type.equals("Doctor")){
        View view= inflater.inflate(R.layout.fragment_hospitals_d, container, false);
           recyclerview=view.findViewById(R.id.recview1);
           database=FirebaseDatabase.getInstance().getReference("Hospitals");
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

            arrayList=new ArrayList<>();


            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Hospital hosp = dataSnapshot.getValue(Hospital.class);
                        arrayList.add(hosp);
                    }
                    myAdapter=new MyHospitals(arrayList);
                    recyclerview.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
                });

                hosp=view.findViewById(R.id.addhosp);
        hosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Map2.class);
                startActivity(intent);

            }
        });
        return view;
        }

        else {
            View view= inflater.inflate(R.layout.fragment_hospitals_p, container, false);
            recyclerview=view.findViewById(R.id.recview2);
            database=FirebaseDatabase.getInstance().getReference("Hospitals");
            recyclerview.setHasFixedSize(true);
            recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            arrayList=new ArrayList<>();
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Hospital hosp = dataSnapshot.getValue(Hospital.class);
                        arrayList.add(hosp);
                    }
                    myAdapter=new MyHospitals(arrayList);
                    recyclerview.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            return view;
        }

        // Inflate the layout for this fragment

    }

}