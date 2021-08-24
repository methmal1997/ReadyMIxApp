package com.example.readymix;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity {
    EditText username,usermail,regpass,reconpass;
    Button bk;
    public  boolean valid=true;
    CheckBox isuser,isadmin;
    FirebaseAuth fAuth;
    RadioButton radio;
    FirebaseFirestore fstore;
    RadioGroup group;
    int s=5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        isuser=findViewById(R.id.chuser);
        isadmin=findViewById(R.id.cadmin);
        username=findViewById(R.id.username);
        usermail=findViewById(R.id.mail);
        regpass=findViewById(R.id.pass1);
        reconpass=findViewById(R.id.conpass1);
        bk=findViewById(R.id.bs1);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();



        isadmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isadmin.isChecked()){
                    isuser.setChecked(false);

                }
            }});
        isuser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isuser.isChecked()){
                    isadmin.setChecked(false);

                }
            }});







        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String pusername = username.getText().toString();
                String pusermail = usermail.getText().toString();
                String ppass = regpass.getText().toString();
                String pconpass = reconpass.getText().toString();



                if (pusername.isEmpty()) {
                    username.setError("Full name is required");
                    return;
                }

                if (pusermail.isEmpty()) {
                    usermail.setError("Email is required");
                    return;
                }

                if (ppass.isEmpty()) {
                    regpass.setError("password is required");
                    return;
                }

                if (pconpass.isEmpty()) {
                    reconpass.setError("Confirm password is required");
                    return;
                }
                if (!ppass.equals(pconpass)) {
                    reconpass.setError("Password is not match");
                    return;
                }

                if (!(isadmin.isChecked() || isuser.isChecked())){
                    Toast.makeText(sign_up.this,"SELECT ACCOUNT TYPE",Toast.LENGTH_SHORT).show();
                    return;
                }



                Toast.makeText(sign_up.this, "Data validated", Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(pusermail, ppass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user=fAuth.getCurrentUser();
                        DocumentReference df=fstore.collection("Users").document(user.getUid());
                        Map<String,Object> userinfo=new HashMap<>();
                        userinfo.put("Full name",username.getText().toString());
                        userinfo.put("Email",usermail.getText().toString());
                        if (isuser.isChecked()) {
                            userinfo.put("IsUser", "1");
                        }
                        if (isadmin.isChecked()){
                            userinfo.put("IsAdmin","1");
                        }

                        df.set(userinfo);


                        startActivity(new Intent(sign_up.this,verify.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(sign_up.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });


    }



}










