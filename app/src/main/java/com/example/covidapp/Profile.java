package com.example.covidapp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    TextView name, age, phone;
    Button edit;
    ImageView image, call;
    DatabaseReference reference;
    Doctor doctor;
    Patient patient;
    Button photo;
    FirebaseAuth mAuth;

    private StorageReference mStorageReference;
    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;


    //    String cameraPermission[];
//    String storagePermission[];
    //  ActivityMainBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String Document_img1="";
    public static final String KEY_User_Document1 = "doc1";
    Uri url ;
    Uri imageUri;



    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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

    @SuppressLint("WrongThread")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_p, container, false);
//        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //super.onCreate(bundle);
        //getMapAsync(this);
        // binding = ActivityMainBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        call=view.findViewById(R.id.image_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),Call.class);
                startActivity(intent);
            }
        });
       downloadImage();
        try {
            final File localFile;

            String prfx = mAuth.getUid().toString();
            localFile = File.createTempFile(prfx, "png");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Uri downloadUri = taskSnapshot.getDownloadUrl();
                    // Picasso.with(getActivity()).load(downloadUri).into(image);

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) view.findViewById(R.id.imageView)).setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        name = (TextView) view.findViewById(R.id.tv1);
        age = (TextView) view.findViewById(R.id.tv2);
        phone = (TextView) view.findViewById(R.id.tv3);
        edit = (Button) view.findViewById(R.id.button);
        image = (ImageView) view.findViewById(R.id.imageView);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showDialog();
                Intent intent = new Intent((Activity) getActivity(), Edit.class);
                startActivity(intent);
            }
        });
        if (MainActivity.type.equals("Patient")) {
            reference = FirebaseDatabase.getInstance().getReference().child("Patients").child(mAuth.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (snapshot.getValue() != null) {
                            patient = (Patient) snapshot.getValue(Patient.class);
                            System.out.println(patient.getName());
                            String name2 = patient.getName();
                            System.out.println(name2);
                            if (name != null) {
                                name.setText(patient.getName());
                            }
                            if (age != null) {
                                age.setText(patient.getAge() + " ");
                            }
                            if (phone != null) {
                                phone.setText(patient.getPhone());
                            }

                        }
                    }


                    //   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            reference = FirebaseDatabase.getInstance().getReference().child("Doctors").child(mAuth.getUid().toString());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (snapshot.getValue() != null) {
                            doctor = (Doctor) snapshot.getValue(Doctor.class);
                            if (name != null) {
                                name.setText(doctor.getName());
                            }
                            if (age != null) {
                                age.setText(doctor.getAge() + " ");
                            }
                            if (phone != null) {
                                phone.setText(doctor.getPhone());
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        if (image != null) {
            //System.out.println("OKAY");
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectImage();


//                    if (Document_img1.equals("") || Document_img1.equals(null)) {
//                   //     ContextThemeWrapper ctw = new ContextThemeWrapper( getActivity(), R.style.Theme_AlertDialog);
//                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
//                        alertDialogBuilder.setTitle("Image Can't Be Empty ");
//                        alertDialogBuilder.setMessage("Image Can't be empty please select any one document");
//                        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//                        alertDialogBuilder.show();
//                        return;
//                    }
                    //uploadImage();



                }
            });
        }
        return view;
    }
    private void uploadPhoto(Bitmap imageBitmap) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        String upload_name = mAuth.getUid().toString();
        StorageReference ref = mStorageReference.child("images");
        ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
        ref.child(upload_name+".jpeg").putBytes(outputStream.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void uploadImage()
//    {
//        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
//        mAuth=FirebaseAuth.getInstance();
//        if (filePath != null) {
//
//            // Code for showing progressDialog while uploading
//            ProgressDialog progressDialog
//                    = new ProgressDialog(getActivity());
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            // Defining the child of storageReference
//            StorageReference ref
//                    = storageReference
//                    .child(
//                            "images/"
//                                    + mAuth.getUid().toString());
//
//            //  bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
//            // adding listeners on upload
//            // or failure of image
//            ref.putFile(filePath)
//                    .addOnSuccessListener(
//                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                                @Override
//                                public void onSuccess(
//                                        UploadTask.TaskSnapshot taskSnapshot)
//                                {
//
//                                    // Image uploaded successfully
//                                    // Dismiss dialog
//                                    progressDialog.dismiss();
//                                    Toast
//                                            .makeText(getActivity(),
//                                                    "Image Uploaded!!",
//                                                    Toast.LENGTH_SHORT)
//                                            .show();
//                                }
//                            })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e)
//                        {
//
//                            // Error, Image not uploaded
//                            progressDialog.dismiss();
//                            Toast
//                                    .makeText(getActivity(),
//                                            "Failed " + e.getMessage(),
//                                            Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//                    })
//                    .addOnProgressListener(
//                            new OnProgressListener<UploadTask.TaskSnapshot>() {
//
//                                // Progress Listener for loading
//                                // percentage on the dialog box
//                                @Override
//                                public void onProgress(
//                                        UploadTask.TaskSnapshot taskSnapshot)
//                                {
//                                    double progress
//                                            = (100.0
//                                            * taskSnapshot.getBytesTransferred()
//                                            / taskSnapshot.getTotalByteCount());
//                                    progressDialog.setMessage(
//                                            "Uploaded "
//                                                    + (int)progress + "%");
//                                }
//                            });
//        }
//    }


    private void selectImage() {
      //  Bitmap photo = (Bitmap) data.getExtras().get("data");
            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo"))
                    {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 1);
//
                    }
                    else if (options[item].equals("Choose from Gallery"))
                    {
                        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                    else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

        }

        @SuppressLint("LongLogTag")
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(photo);
                    uploadPhoto(photo);

                } else if (requestCode == 2) {
                    Uri selectedImage = data.getData();
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    thumbnail=getResizedBitmap(thumbnail, 400);
                    Log.w("path of image from gallery......******************.........", picturePath+"");
                    image.setImageBitmap(thumbnail);
                    BitMapToString(thumbnail);
                    uploadPhoto(thumbnail);
                }
            }

        }
        public String BitMapToString(Bitmap userImage1) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] b = baos.toByteArray();
            Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
            return Document_img1;
        }
    private void downloadImage() {
        StorageReference profilePhoto = mStorageReference.child("images/"+mAuth.getUid()+".jpeg");
        Long bytes = 1024*1024l;
        profilePhoto.getBytes(bytes).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                image.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
        public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float)width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }

            }









