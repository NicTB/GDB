package com.example.nicta.gdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        GdbBDD gdbBDD = new GdbBDD(this);



    }
}
