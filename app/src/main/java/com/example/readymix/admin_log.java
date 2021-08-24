package com.example.readymix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_log extends AppCompatActivity {
    Button ba1;
    EditText email_user,email_pass;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log);



        ba1 =findViewById(R.id.ba1);
        email_user=findViewById(R.id.name1);
        email_pass=findViewById(R.id.pass1);
        firebaseauth= FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();


        ba1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                

                if (email_user.getText().toString().isEmpty()){
                    email_user.setError("Email is missing");
                    return;
                }if (email_pass.getText().toString().isEmpty()){
                    email_pass.setError("password is missing");
                    return;
                }

                firebaseauth.signInWithEmailAndPassword(email_user.getText().toString(),email_pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(firebaseauth.getCurrentUser().isEmailVerified()){
                            checkAccessed(authResult.getUser().getUid());
                        }
                        else {
                            Toast.makeText(admin_log.this,"Please confirm your email",Toast.LENGTH_SHORT).show();
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(admin_log.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }



    private void checkAccessed(String uid) {
        DocumentReference df=fstore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+ documentSnapshot.getData()  );
                if (documentSnapshot.getString("IsAdmin") !=null) {
                    startActivity(new Intent(admin_log.this, choose.class));
                    finish();}

                else if (documentSnapshot.getString("IsAdmin")!=null){
                    Toast.makeText(admin_log.this,"Use relevant account",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(admin_log.this,"Something is wrong",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}


