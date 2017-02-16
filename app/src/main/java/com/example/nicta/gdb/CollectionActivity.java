package com.example.nicta.gdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Button btnFiltre = (Button) findViewById(R.id.btnFiltre);
        Button btnChercher = (Button) findViewById(R.id.btnChercher);



        /*
        btnFiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carte c1 = new Carte("adrenaline_rush");
                AfficherCarteActivity cdd = new AfficherCarteActivity(CollectionActivity.this, c1);
                cdd.show();
            }
        });

        btnChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carte c2 = new Carte("aeromancy");
                AfficherCarteActivity cdd = new AfficherCarteActivity(CollectionActivity.this, c2);
                cdd.show();
            }
        });*/
    }
}
