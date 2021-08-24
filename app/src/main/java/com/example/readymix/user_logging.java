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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class user_logging extends AppCompatActivity {

    EditText email_user,email_pass;
    Button b1;
    FirebaseAuth firebaseauth;
    AuthResult authResult;
    FirebaseFirestore fstore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logging);

        email_user=findViewById(R.id.name_email1);
        email_pass=findViewById(R.id.email_pass1);
        b1=findViewById(R.id.b1);
        firebaseauth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
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
                            checkAccessed(authResult.getUser().getUid());}
                        else {
                            Toast.makeText(user_logging.this,"Please confirm your email",Toast.LENGTH_SHORT).show();
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(user_logging.this,e.getMessage(),Toast.LENGTH_SHORT).show();

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
                if (documentSnapshot.getString("IsUser") !=null) {
                    startActivity(new Intent(user_logging.this, calender.class));
                    finish();
                /*}
                else if (documentSnapshot.getString("IsAdmin")!=null){
                    Toast.makeText(User_logging.this,"Use relevant account",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(User_logging.this,"Something wrong",Toast.LENGTH_SHORT).show();
                }*/
                }
            }
        });

    }
}





