package com.example.covidapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyHospitals extends RecyclerView.Adapter <MyHospitals.MyViewHolder>{
    private ArrayList<Hospital> arrayList;
    Context context;
    DatabaseReference  reference;
    Hospital hosp;
    Hospital hospital;
    String date;
    double lat;
    double lang;



    public MyHospitals(ArrayList<Hospital> arrayList){
        this.arrayList=arrayList;
    }



    @NonNull
    @Override
    public MyHospitals.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hosp,parent,false);
        context = parent.getContext();
        return new MyHospitals.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHospitals.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        hosp =arrayList.get(position);
        date=hosp.getDate();
        System.out.println(date);
        reference= FirebaseDatabase.getInstance().getReference("Hospitals").child(date);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                     hospital = snapshot.getValue(Hospital.class);
                     holder.Name.setText(hospital.getName());
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hosp =arrayList.get(position);
                date=hosp.getDate();
                System.out.println(date.toString());
                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(date.toString());
//                Intent intent =new Intent(context,Tabs2.class);
//                intent.putExtra("reference",database.toString());
//                context.startActivity(intent);
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (snapshot.getValue() != null) {
                                Hospital hospital1 = (Hospital) snapshot.getValue(Hospital.class);
                                System.out.println(hospital1.toString());
                                 lat=hospital1.getLat();
                                 System.out.println(lat);
                                 lang=hospital1.getLang();
                                 showLocation();


                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }
    });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    private void showLocation() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /////make map clear
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        dialog.setContentView(R.layout.location_hosp);////your custom content

        MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
        MapsInitializer.initialize(context);

        mMapView.onCreate(dialog.onSaveInstanceState());
        mMapView.onResume();


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng posisiabsen = new LatLng(lat, lang); ////your lat lng
                googleMap.addMarker(new MarkerOptions().position(posisiabsen).title("Your title"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(posisiabsen));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }


        });

Button directions_button=(Button)dialog.findViewById(R.id.button11);
        directions_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination="google.navigation:q="+lat+","+lang;
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse(destination));
                if(intent.resolveActivity(context.getPackageManager()) != null){
                    context.startActivity(intent);
                }
            }
        });
        Button dialogButton = (Button) dialog.findViewById(R.id.button10);
 //if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

            }
    public class MyViewHolder extends RecyclerView.ViewHolder {
     TextView Name;
     CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card1);
            Name=itemView.findViewById(R.id.hosp_name);
        }
    }
}
