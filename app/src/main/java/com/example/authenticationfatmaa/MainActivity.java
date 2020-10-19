package com.example.authenticationfatmaa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView submit ,textView2 ,uploadbtn;
    EditText email , pwd ,fullname ;
    Button but;
    ProgressBar progress;
    FirebaseAuth mAuth;
    User user;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser userkey;

    private String Email;
    private String Name;
    public Uri imageUri;

   ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       storage=FirebaseStorage.getInstance();
       storageReference=storage.getReference();
        progress=findViewById(R.id.progressBar);
        uploadbtn = findViewById(R.id.upload);
        fullname=findViewById(R.id.FullName);
        //submit=findViewById(R.id.signUp);
        mAuth = FirebaseAuth.getInstance();
        but = findViewById(R.id.button);
        email =findViewById(R.id.EmailAddress);
        pwd =findViewById(R.id.Password);
        profileImage=findViewById(R.id.profileimage);

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("users");

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();


            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email =email.getText().toString().trim();
                String Pwd = pwd.getText().toString().trim();
                Name =fullname.getText().toString();
                if(Email.isEmpty()){
                    email.setError("Please enter email id");
                    email.requestFocus();
                }


                else if (Pwd.isEmpty()){
                    pwd.setError("please enter your password");
                    pwd.requestFocus();

                }
                else if (Name.isEmpty()) {
                    fullname.setError("please enter your Full name");
                    fullname.requestFocus();
                }
                else if (Pwd.isEmpty() && Email.isEmpty()){
                    Toast.makeText(MainActivity.this, "enter your email and password", Toast.LENGTH_SHORT).show();
                }
                else if( !(Pwd.isEmpty() && Email.isEmpty() && Name.isEmpty())){
                progress.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(Email,Pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "SignUp Unseccessful , please try again", Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);

                            }else{
                                userkey = FirebaseAuth.getInstance().getCurrentUser() ;

                                user = new User(Name , Email);

                                myRef.child(userkey.getUid()).setValue(user);

                                startActivity(new Intent(MainActivity.this, HomeActivity.class));


                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Error Ocurred !", Toast.LENGTH_SHORT).show();

                }
            }

    });

    }

    private void choosePicture(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select picture"),1);

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            profileImage.setImageURI(imageUri);
            uploadPicture();

        }
    }

    private void uploadPicture() {
         final ProgressDialog pd = new ProgressDialog(this);
         pd.setTitle("Uploading..");
         pd.show();
         final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+ randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //pd.dismiss();
                   Snackbar.make(findViewById(android.R.id.content),"Image Uploaded.", Snackbar.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                      Toast.makeText(getApplicationContext(),"Image Failed",Toast.LENGTH_LONG).show();
                    }
                })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress( @NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent= (1000.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage("progress: " + (int) progressPercent + "%");
            }
        });
    }


}