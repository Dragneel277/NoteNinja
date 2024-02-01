package com.example.noteninja;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;


public class NoteDetails extends AppCompatActivity {
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        data = getIntent();


        TextView content = findViewById(R.id.noteDetailsContent);
        TextView title = findViewById(R.id.noteDetailsTitle);
        content.setMovementMethod(new ScrollingMovementMethod());

        content.setText(data.getStringExtra("content"));
        title.setText(data.getStringExtra("title"));
        content.setBackgroundColor(getResources().getColor(data.getIntExtra("code",0),null));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            Intent i = new Intent(view.getContext(),EditNote.class);
            i.putExtra("title",data.getStringExtra("title"));
            i.putExtra("content",data.getStringExtra("content"));
            i.putExtra("noteId",data.getStringExtra("noteId"));
            startActivity(i);
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        OnBackPressedDispatcher onBackPressedDispatcher = null;
        if(item.getItemId() == android.R.id.home){

            onBackPressedDispatcher.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
