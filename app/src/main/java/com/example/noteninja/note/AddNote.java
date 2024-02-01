package com.example.noteninja.note;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.noteninja.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/** @noinspection DataFlowIssue*/
public class AddNote extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText noteTitle,noteContent;
    ProgressBar progressBarSave;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OnBackPressedDispatcher onBackPressedDispatcher = null;

        fStore = FirebaseFirestore.getInstance();
        noteContent = findViewById(R.id.addNoteContent);
        noteTitle = findViewById(R.id.addNoteTitle);
        progressBarSave = findViewById(R.id.progressBar);

        user = FirebaseAuth.getInstance().getCurrentUser();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            String nTitle = noteTitle.getText().toString();
            String nContent = noteContent.getText().toString();

            if(nTitle.isEmpty() || nContent.isEmpty()){
                Toast.makeText(AddNote.this, "Can not Save note with Empty Field.", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBarSave.setVisibility(View.VISIBLE);

            // save note on firebase

            Map<String,Object> note = new HashMap<>();
            note.put("title",nTitle);
            note.put("content",nContent);


            fStore.collection("notes").add(note).addOnSuccessListener(documentReference -> {
                Toast.makeText(AddNote.this, "Note Added.", Toast.LENGTH_SHORT).show();
                onBackPressedDispatcher.onBackPressed();
            })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddNote.this, "Error, Try again.", Toast.LENGTH_SHORT).show();
                        progressBarSave.setVisibility(View.VISIBLE);
                    });

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.close_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.close){
            Toast.makeText(this,"Not Saved.", Toast.LENGTH_SHORT).show();


            OnBackPressedDispatcher onBackPressedDispatcher = null;
            onBackPressedDispatcher.onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}
