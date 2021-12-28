package com.example.covidapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment extends Fragment {
private Button addRequest;
private EditText reqText;
FirebaseDatabase rootnode;
DatabaseReference reference;
private FirebaseAuth mAuth;
private int count;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Requests.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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
       // setContentView(R.layout.activity_main);
      // addRequest=(Button)findViewById(R.id.requesttext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_requests, container, false);
        addRequest = view.findViewById(R.id.button2);
        reqText=view.findViewById(R.id.reqtext);
        mAuth=FirebaseAuth.getInstance();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= (NotificationManager)getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date=new Date();
                String symptoms= reqText.getText().toString();
                rootnode=FirebaseDatabase.getInstance();
                reference=rootnode.getReference();
                reference.child(mAuth.getUid());
                Request request=new Request(mAuth.getUid(),symptoms,false);
                request.setDate(date.toString());
                reference.child("Patients").child(mAuth.getUid()).child("Requests").child(date.toString()).setValue(request);
                reference.child("All Requests").child(date.toString()).setValue(request);

                NotificationCompat.Builder builder=new NotificationCompat.Builder(getActivity(), "My Notification");
                builder.setContentTitle(" Request");
                builder.setContentText("new request added");
                builder.setSmallIcon(R.drawable.picssss);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity().getApplicationContext());
                managerCompat.notify(1,builder.build());



            }
        });
        return  view;
    }
}