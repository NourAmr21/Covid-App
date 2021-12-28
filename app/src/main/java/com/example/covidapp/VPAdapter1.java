package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPAdapter1 extends FragmentStateAdapter {
    public VPAdapter1(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new RequestFragment_d2();//requests
            case 2:
                return new HospitalsFragment();

        }
        return new Profile();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}






//
//public class VPAdapter1 extends FirebaseRecyclerAdapter<
//        Request, VPAdapter1.RequestViewHolder> {
//
//    public VPAdapter1(
//            @NonNull FragmentManager options) {
//        super(options);
//    }
//    public static class RequestViewHolder extends  RecyclerView.ViewHolder{
//        TextView userName;
//        TextView userAge;
//        TextView requesttext;
//        //  CircleImageView profileImage;
//        // Button AcceptButton , CancelButton;
//        public RequestViewHolder(@NonNull View itemView) {
//            super(itemView);
//            userName=itemView.findViewById(R.id.textView8);
//            userAge=itemView.findViewById(R.id.textView9);
//            requesttext=itemView.findViewById(R.id.textView10);
//        }
//    }
//    @Override
//    protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull Request model) {
//        holder.itemView.findViewById(R.id.replybutton).setVisibility(View.VISIBLE);
//        final String list_req=getRef(position).getKey();
//        DatabaseReference getType=getRef(position).child("All Requests").child("closed").getRef();
//        DatabaseReference getReq=getRef(position).child("All Requests").child("request").getRef();
//        getType.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String type= snapshot.getValue().toString();
//                    if(type.equals("false")){
//                        UsersRef.child(list_req).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                // if(snapshot.hasChild("image")){
//                                final String requestUserName=snapshot.child("name").getValue().toString();
//                                final String requestUserAge=snapshot.child("age").getValue().toString();
//
//                                holder.userName.setText(requestUserName);
//                                holder.userAge.setText(requestUserAge);
//
//
////                                                myRequestList.setAdapter(adapter);
////                                                adapter.startListening();
//                                //  }
//                                // else{
//
//                                // }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    @NonNull
//    @Override
//    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_request_d , parent , false);
//        RequestViewHolder holder=new RequestViewHolder(view);
//        return holder;
//    }
//};
//}