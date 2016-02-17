package com.example.manuel.mapnote3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    TextView titulo, nota, localizacion;
    ImageView photoNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);


        titulo = (TextView) findViewById(R.id.detailTitle);
        nota = (TextView) findViewById(R.id.detailNote);
        localizacion = (TextView) findViewById(R.id.detailLocation);
        photoNote = (ImageView)findViewById(R.id.photoNote);

        //Recogemos el intent con la id de la nota
        String refString = this.getIntent().getStringExtra("nota_ref");

        Firebase ref = new Firebase(refString);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Note note = dataSnapshot.getValue(Note.class);

                titulo.setText(note.getTitle());
                nota.setText(note.getNota());
                localizacion.setText("Loc: " + note.getLongitud() + ", " + note.getLatitud());

                if(note.getImagePath()!= null){
                    File imagePath = new File(note.getImagePath());
                    Picasso.with(getBaseContext()).load(imagePath).fit().into(photoNote);
                }else{
                    Picasso.with(getBaseContext()).load(R.drawable.no_image).fit().into(photoNote);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
