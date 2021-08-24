package com.example.readymix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        final Button bc1=findViewById(R.id.c1);
        bc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ic1=new Intent(choose.this,current_user.class);
                startActivity(ic1);
            }
        });
        final Button bc2=findViewById(R.id.c2);
        bc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ic2=new Intent(choose.this,sign_up.class);
                startActivity(ic2);
                finish();
            }
        });
    }
}