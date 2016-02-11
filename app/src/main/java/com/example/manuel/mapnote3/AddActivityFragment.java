package com.example.manuel.mapnote3;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.client.Firebase;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddActivityFragment extends Fragment implements LocationListener {

    EditText addTitle, addNote;
    Location loc = null;

    public AddActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add, container, false);

        addTitle = (EditText) view.findViewById(R.id.addTitle);
        addNote = (EditText) view.findViewById(R.id.addNote);

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

                note.setValue(nota);

                //Vaciamos el editTextNota
                addTitle.setText("");
                addNote.setText("");

            }
        });

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        loc = location;
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
}
