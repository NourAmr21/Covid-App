package com.example.covidapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFragment_d2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment_d2 extends Fragment {
    RecyclerView recyclerview;
    DatabaseReference database;
   // DatabaseReference database2;
    MyAdapter myAdapter;
    ArrayList<Request> arrayList;
    FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequestFragment_d2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestFragment_d.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment_d2 newInstance(String param1, String param2) {
        RequestFragment_d2 fragment = new RequestFragment_d2();
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



        View view=inflater.inflate(R.layout.fragment_request_d2, container, false);
        recyclerview=view.findViewById(R.id.reqlist);
        mAuth = FirebaseAuth.getInstance();
        if(MainActivity.type.equals("Doctor")){
            database= FirebaseDatabase.getInstance().getReference("All Requests");
        }
        else{
            database=FirebaseDatabase.getInstance().getReference("Patients").child((mAuth).getUid().toString()).child("Requests");
        }
       // database= FirebaseDatabase.getInstance().getReference("All Requests");
       // database2= FirebaseDatabase.getInstance().getReference("Patients").child(String.valueOf(mAuth)).child("Requests");
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        arrayList=new ArrayList<>();


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Request req = dataSnapshot.getValue(Request.class);
                    if(!req.isClosed()&& MainActivity.type.equals("Doctor")){
                        arrayList.add(req);
                    }
                    if(MainActivity.type.equals("Patient")){
                        arrayList.add(req);
                    }

                }
                myAdapter=new MyAdapter(arrayList);
                recyclerview.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // Inflate the layout for this fragment


        });

        return view;
    }
}