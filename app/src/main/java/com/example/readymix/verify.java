package com.example.readymix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class verify extends AppCompatActivity {

    TextView verifymsg;
    Button verifybtton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        auth=FirebaseAuth.getInstance();

        verifymsg=findViewById(R.id.verifytext);
        verifybtton=findViewById(R.id.verify);

        if (!auth.getCurrentUser().isEmailVerified()){
            verifymsg.setVisibility(View.VISIBLE);
            verifymsg.setVisibility(View.VISIBLE);
        }
        verifybtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(verify.this,"Verification Email sent",Toast.LENGTH_SHORT).show();
                            verifymsg.setVisibility(View.INVISIBLE);
                            verifybtton.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(verify.this,Choose.class));
                            finish();
                        }else {
                            Toast.makeText(verify.this,task.getException().getMessage(),Toast.LENGTH_LONG);
                        }
                    }


                });
                */
                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(verify.this,"Verification Email sent",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(verify.this,choose.class));
                            finish();
                        }
                    }
                });

            }
        });

    }
}

