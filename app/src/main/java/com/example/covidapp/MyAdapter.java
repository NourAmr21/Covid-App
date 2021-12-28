package com.example.covidapp;

import static androidx.core.app.NotificationCompat.Builder.*;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter  extends RecyclerView.Adapter <MyAdapter.MyViewHolder>{
    private ArrayList<Request> arrayList;
    Context context;
    Patient patient;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String date;
    String pid;
    final String [] fields = new String[2];
    public MyAdapter(ArrayList<Request> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        context = parent.getContext();
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        Request req =arrayList.get(position);
        pid=req.getPatient().toString();
         date=req.getDate();
         String pid;
     //  FirebaseAuth mAuth = FirebaseAuth.getInstance();
       FirebaseDatabase rootnode= FirebaseDatabase.getInstance();
//        Toast.makeText(context, req.getPatient(), Toast.LENGTH_LONG).show();
        if(MainActivity.type.equals("Doctor")) {
             reference = rootnode.getReference("Patients").child(req.getPatient());

//       nameStr = rootnode.getReference(req.getPatient()).child("name");
        }
        else{
            reference = rootnode.getReference("Patients").child(mAuth.getUid().toString());
        }
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        patient = snapshot.getValue(Patient.class);
                        holder.Name.setText(patient.getName());
                        holder.age.setText(patient.getAge() + "");
                        if(req.isClosed()){
                            String c="Closed Request";
                            holder.status.setText(c);
                        }
                        else{
                            String c="Pending Request";
                            holder.status.setText(c);
                        }

                        return;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



    // int resource=userList.get()

        //String n1=reference.child(req.getPatient()).child("name").toString();
       // patient=snapshot.getValue(Patient.class);
//        if(patient!=null) {
        Toast.makeText(context, fields[0]+"test", Toast.LENGTH_SHORT).show();
//        }
        holder.symptoms.setText(req.getRequest());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.type.equals("Doctor")){
                showDialog();
                }
                else{
                    showReply();
                }

            }
        });
     //   holder.age.setText()

    }
private void showReply() {
    Dialog rdialog = new Dialog(context);
    rdialog.setContentView(R.layout.reply);
    TextView rt = rdialog.findViewById(R.id.replyTxt1);
    Button cancel = rdialog.findViewById(R.id.button6);
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Patients").child(mAuth.getUid()).child("Requests").child(date.toString());
    database.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                if (snapshot.getValue() != null) {
                    Request req = (Request) snapshot.getValue(Request.class);
                    rt.setText(req.getReply());

                }

            }
        }



        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });



    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            rdialog.dismiss();
        }
    });
    rdialog.show();
}



        private void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        EditText replyTxt = dialog.findViewById(R.id.replyTxt);
        Button send = dialog.findViewById(R.id.sendBtn) , cancel = dialog.findViewById(R.id.cancelBtn);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("My Notification","My Notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= (NotificationManager) context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = replyTxt.getText().toString();
                if (res.isEmpty())
                    Toast.makeText(context, "No reply !", Toast.LENGTH_SHORT).show();
                else {

                    DatabaseReference node=FirebaseDatabase.getInstance().getReference().child("Patients").child(pid);
                    DatabaseReference node1=FirebaseDatabase.getInstance().getReference().child("All Requests");
                    node.child("Requests").child(date).child("reply").setValue(res);
                    node.child("Requests").child(date).child("closed").setValue(true);
                    node1.child(date).child("reply").setValue(res);
                    node1.child(date).child("closed").setValue(true);
                    //node.child("Requests")
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                    Builder builder=new Builder(context.getApplicationContext(), "My Notification");
                    builder.setContentTitle(" Replied");
                    builder.setContentText("new reply added");
                    builder.setSmallIcon(R.drawable.picssss);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context.getApplicationContext());
                    managerCompat.notify(1,builder.build());
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView symptoms;
        CardView card;
        TextView Name;
        TextView age;
        TextView status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            symptoms=itemView.findViewById(R.id.textView10);
            Name=itemView.findViewById(R.id.textView8);
            age=itemView.findViewById(R.id.textView9);
            status=itemView.findViewById(R.id.textView14);
            card = itemView.findViewById(R.id.card);
        }
    }
    private void removeItem(int position){

    }
}
