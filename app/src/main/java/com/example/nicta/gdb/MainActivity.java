package com.example.nicta.gdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FournisseurCartes fc = FournisseurCartes.getInstance();
        fc.chercherCartes();

        Button btnCollection;
        Button btnCreation;

        btnCollection = (Button)findViewById(R.id.btnCollectionCartes);
        btnCreation = (Button)findViewById(R.id.btnCreationDeck);

        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CollectionActivity.class);
                startActivity(intent);
            }
        });
        btnCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreationActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);

    }
}
