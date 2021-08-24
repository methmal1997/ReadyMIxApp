package com.example.readymix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.OAEPParameterSpec;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class copy_event extends AppCompatActivity {
    private static final String TAG = "event";
    private static final String Event1="event1";
    private static final String Event2="event2";
    private static final String Event3="event3";
    private static final String Event4="event4";
    private static final String Event5="event5";
    private static final String Event6="event6";
    private static final String Event7="event7";
    private static final String Event8="event8";

    private EditText event1;
    private EditText event2;
    private EditText event3;
    private EditText event4;
    private EditText event5;
    private EditText event6;
    private EditText event7;
    private EditText event8;

    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;



    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference drefnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copy_event);
        TextView v1=findViewById(R.id.txtdate);

        event1=findViewById(R.id.eve1);
        event2=findViewById(R.id.eve2);
        event3=findViewById(R.id.eve3);
        event4=findViewById(R.id.eve4);
        event5=findViewById(R.id.eve5);
        event6=findViewById(R.id.eve6);
        event7=findViewById(R.id.eve7);
        event8=findViewById(R.id.eve8);

        t1=findViewById(R.id.te1);
        t2=findViewById(R.id.te2);
        t3=findViewById(R.id.te3);
        t4=findViewById(R.id.te4);
        t5=findViewById(R.id.te5);


        String date=getIntent().getStringExtra("date");
        v1.setText(date);
        DocumentReference drefnote=db.collection("Notebook").document(date);

        Button b9=findViewById(R.id.b9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i9=new Intent(copy_event.this,calender.class);
                startActivity(i9);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        String date=getIntent().getStringExtra("date");
        drefnote=db.collection("Notebook").document(date);
        drefnote.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot,FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(copy_event.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }
                if (documentSnapshot.exists()) {
                    String ep1 = documentSnapshot.getString(Event1);
                    String ep2 = documentSnapshot.getString(Event2);
                    String ep3 = documentSnapshot.getString(Event3);
                    String ep4 = documentSnapshot.getString(Event4);
                    String ep5 = documentSnapshot.getString(Event5);

                    //Map<String, Object> note = documentSnapshot.getData();
                    t1.setText("Event1: " + ep1);
                    t2.setText("Event1: " + ep2);
                    t3.setText("Event1: " + ep3);
                    t4.setText("Event1: " + ep4);
                    t5.setText("Event1: " + ep5);
                }

            }

        });

    }

    public void saveNote(View v){
        String date=getIntent().getStringExtra("date");
        String u1=event1.getText().toString();
        String u2=event2.getText().toString();
        String u3=event3.getText().toString();
        String u4=event4.getText().toString();
        String u5=event5.getText().toString();
        String u6=event6.getText().toString();
        String u7=event7.getText().toString();
        String u8=event8.getText().toString();

        Map<String,Object> note=new HashMap<>();
        note.put(Event1,u1);
        note.put(Event2,u2);
        note.put(Event3,u3);
        note.put(Event4,u4);
        note.put(Event5,u5);
        note.put(Event6,u6);
        note.put(Event7,u7);
        note.put(Event8,u8);

        db.collection("Notebook").document(date).set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(copy_event.this,"Saved data",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(copy_event.this,"Error",Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }
    public void loadNote(View v) {
        String date = getIntent().getStringExtra("date");
        db.collection("Notebook").document(date).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String ep1 = documentSnapshot.getString(Event1);
                            String ep2 = documentSnapshot.getString(Event2);
                            String ep3 = documentSnapshot.getString(Event3);
                            String ep4 = documentSnapshot.getString(Event4);
                            String ep5 = documentSnapshot.getString(Event5);

                            //Map<String, Object> note = documentSnapshot.getData();
                            t1.setText("Event1: " + ep1);
                            t2.setText("Event1: " + ep2);
                            t3.setText("Event1: " + ep3);
                            t4.setText("Event1: " + ep4);
                            t5.setText("Event1: " + ep5);
                        } else {
                            Toast.makeText(copy_event.this, "Document doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(copy_event.this, "Error!!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}


