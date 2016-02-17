package com.example.manuel.mapnote3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddActivityFragment extends Fragment implements LocationListener {

    EditText addTitle, addNote;
    Location loc = null;
    ProgressDialog progress;
    ImageButton cameraButton, galleryButton, micButton;
    ImageView checkGallery, checkCamera, checkMic;
    boolean tookPhoto = false;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public AddActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add, container, false);

        addTitle = (EditText) view.findViewById(R.id.addTitle);
        addNote = (EditText) view.findViewById(R.id.addNote);
        checkGallery = (ImageView) view.findViewById(R.id.checkGallery);
        checkCamera = (ImageView) view.findViewById(R.id.checkCamera);
        checkMic = (ImageView) view.findViewById(R.id.checkMic);
        checkGallery.setVisibility(View.INVISIBLE);
        checkCamera.setVisibility(View.INVISIBLE);
        checkMic.setVisibility(View.INVISIBLE);

        //Imagebuttons. Pero tambien se podria hacer con el layout qque coge el imagebutton y el texto
        galleryButton = (ImageButton) view.findViewById(R.id.galleryButton);
        cameraButton = (ImageButton) view.findViewById(R.id.cameraButton);
        micButton = (ImageButton) view.findViewById(R.id.micButton);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Attached picture", Toast.LENGTH_SHORT).show();
                checkGallery.setVisibility(View.VISIBLE);

            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
                checkCamera.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Attached photo", Toast.LENGTH_SHORT).show();
            }
        });
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMic();
                checkMic.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Attached voice note", Toast.LENGTH_SHORT).show();
            }
        });

        //ProgressDialog que se muestra hasta que la aplicacion coge la localizacion
        progress = new ProgressDialog(getContext());
        progress.setMessage("Locating");
        progress.show();

        //Le decimos a Firebase que este sera el contexto
        Firebase.setAndroidContext(getContext());

        //Creamos una referencia a nuestra bd de Firebase
        Firebase ref = new Firebase("https://mapnote.firebaseio.com/");

        //Y tendra un hijo que es donde guardara las notas y la localizacion de esta
        final Firebase notes = ref.child("notes");

        FloatingActionButton sendNote = (FloatingActionButton) view.findViewById(R.id.sendNote);
        sendNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Firebase note = notes.push();

                Note nota = new Note();
                nota.setTitle(addTitle.getText().toString());
                nota.setNota(addNote.getText().toString());
                nota.setLatitud(loc.getLatitude());
                nota.setLongitud(loc.getLongitude());
                if(tookPhoto)
                {
                    nota.setImagePath(imageFile());

                    //Lo ponemos a false otra vez
                    tookPhoto= false;
                }
                //Instruccion para subir a Firebase
                note.setValue(nota);

                //Vaciamos el editTextNota
                addTitle.setText("");
                addNote.setText("");

                //Escondemos los iconos
                checkGallery.setVisibility(View.INVISIBLE);
                checkCamera.setVisibility(View.INVISIBLE);
                checkMic.setVisibility(View.INVISIBLE);


            }
        });

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        return view;
    }

    //METODOS DE LA INTERFAZ LOCATION LISTENER
    @Override
    public void onLocationChanged(Location location) {
        //Cogemos la localizacion de ese momento
        loc = location;
        //Y como ha encontrado la localizacion escondemos el progress
        progress.hide();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void openCamera(){
        tookPhoto = true;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public String imageFile() {

        //Cogemos el PATH de la ultima foto tomada
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        String path =  cursor.getString(column_index_data);

        return path;
    }

    public void openMic(){
        final int ACTIVITY_RECORD_SOUND = 1;
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
    }

}
