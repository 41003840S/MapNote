package com.example.manuel.mapnote3;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ListView noteList;
    TextView title;
    Intent intent;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        noteList = (ListView) view.findViewById(R.id.notesList);

        //Le decimos a Firebase que este sera el contexto
        Firebase.setAndroidContext(getContext());

        //Creamos una referencia a nuestra bd de Firebase
        Firebase refNotes = new Firebase("https://mapnote.firebaseio.com/").child("notes");

        final FirebaseListAdapter adapter = new FirebaseListAdapter<Note>(getActivity(), Note.class, R.layout.note_row, refNotes) {
            @Override
            protected void populateView(View v, Note model, int position) {
                super.populateView(v, model, position);

                title = (TextView) v.findViewById(R.id.tvTitle);
                title.setText(model.getTitle());

            }
        };

        noteList.setAdapter(adapter);

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Intent intent = new Intent(getContext(), DetailActivity.class);
                Firebase ref = adapter.getRef(position);
                //Toast.makeText(getApplicationContext(), ref.toString(), Toast.LENGTH_LONG).show();
                intent.putExtra("nota_ref", ref.toString());
                startActivity(intent);
            }
        });


        FloatingActionButton addNote = (FloatingActionButton) view.findViewById(R.id.addNote);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
