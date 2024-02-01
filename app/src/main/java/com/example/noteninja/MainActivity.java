package com.example.noteninja;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteninja.model.Note;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    RecyclerView noteLists;

    FirebaseFirestore fStore;
    FirebaseUser user;

    FirestoreRecyclerAdapter<Note,NoteViewHolder> noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fStore = FirebaseFirestore.getInstance();

        Query query = fStore.collection("notes").orderBy("title",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> allNotes = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();

        noteAdapter = new FirestoreRecyclerAdapter<Note, NoteViewHolder>(allNotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, @SuppressLint("RecyclerView") final int i, @NonNull final Note note) {
                noteViewHolder.noteTitle.setText(note.getTitle());
                noteViewHolder.noteContent.setText(note.getContent());
                final int code = getRandomColor();
                noteViewHolder.mCardView.setCardBackgroundColor(noteViewHolder.view.getResources().getColor(code,null));
                final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();

                noteViewHolder.view.setOnClickListener(v -> {
                    Intent i1 = new Intent(v.getContext(), NoteDetails.class);
                    i1.putExtra("title",note.getTitle());
                    i1.putExtra("content",note.getContent());
                    i1.putExtra("code",code);
                    i1.putExtra("noteId",docId);
                    v.getContext().startActivity(i1);
                });

                ImageView menuIcon = noteViewHolder.view.findViewById(R.id.menuIcon);
                menuIcon.setOnClickListener(v -> {
                    final String docId1 = noteAdapter.getSnapshots().getSnapshot(i).getId();
                    PopupMenu menu = new PopupMenu(v.getContext(),v);
                    menu.setGravity(Gravity.END);
                    menu.getMenu().add("Edit").setOnMenuItemClickListener(item -> {
                        Intent i12 = new Intent(v.getContext(), EditNote.class);
                        i12.putExtra("title",note.getTitle());
                        i12.putExtra("content",note.getContent());
                        i12.putExtra("noteId", docId1);
                        startActivity(i12);
                        return false;
                    });

                    menu.getMenu().add("Delete").setOnMenuItemClickListener(item -> {
                        fStore.collection("notes").document(user.getUid()).collection("myNotes").document(docId1)
                                .delete()
                                .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error in Deleting Note.", Toast.LENGTH_SHORT).show());
                        return false;
                    });

                    menu.show();

                });



            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };


        noteLists = findViewById(R.id.notelist);

        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)); //staggered layout displays more in a grid view than a list tip
        noteLists.setAdapter(noteAdapter);

        FloatingActionButton fab = findViewById(R.id.addNoteFloat);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(view.getContext(), AddNote.class));
            overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
            finish();
        });

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addNote) {
            startActivity(new Intent(this, AddNote.class));
        } else {
            Toast.makeText(this, "Coming soon.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "Settings Menu Clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle,noteContent;
        View view;
        CardView mCardView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            mCardView = itemView.findViewById(R.id.noteCard);
            view = itemView;
        }
    }

    // Random colors to notes
    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        {
            colorCode.add(R.color.White);
            colorCode.add(R.color.Ivory);
            colorCode.add(R.color.LightYellow);
            colorCode.add(R.color.Yellow);
            colorCode.add(R.color.Snow);
            colorCode.add(R.color.LemonChiffon);
            colorCode.add(R.color.Seashell);
            colorCode.add(R.color.PapayaWhip);
            colorCode.add(R.color.MistyRose);
            colorCode.add(R.color.Moccasin);
            colorCode.add(R.color.PeachPuff);
            colorCode.add(R.color.Gold);
            colorCode.add(R.color.Pink);
            colorCode.add(R.color.LightPink);
            colorCode.add(R.color.Orange);
            colorCode.add(R.color.LightSalmon);
            colorCode.add(R.color.DarkOrange);
            colorCode.add(R.color.HotPink);
            colorCode.add(R.color.Tomato);
            colorCode.add(R.color.DeepPink);
            colorCode.add(R.color.Fuchsia);
            colorCode.add(R.color.Magenta);
            colorCode.add(R.color.Red);
            colorCode.add(R.color.OldLace);
            colorCode.add(R.color.AntiqueWhite);
            colorCode.add(R.color.GhostWhite);
            colorCode.add(R.color.MintCream);
            colorCode.add(R.color.Azure);
            colorCode.add(R.color.Honeydew);
            colorCode.add(R.color.AliceBlue);
            colorCode.add(R.color.Khaki);
            colorCode.add(R.color.PaleGoldenrod);
            colorCode.add(R.color.Violet);
            colorCode.add(R.color.DarkSalmon);
            colorCode.add(R.color.Lavender);
            colorCode.add(R.color.LightCyan);
            colorCode.add(R.color.BurlyWood);
            colorCode.add(R.color.Plum);
            colorCode.add(R.color.Gainsboro);
            colorCode.add(R.color.Crimson);
            colorCode.add(R.color.PaleVioletRed);
            colorCode.add(R.color.Goldenrod);
            colorCode.add(R.color.Thistle);
            colorCode.add(R.color.LightGrey);
            colorCode.add(R.color.Tan);
            colorCode.add(R.color.IndianRed);
            colorCode.add(R.color.MediumVioletRed);
            colorCode.add(R.color.Silver);
            colorCode.add(R.color.DarkKhaki);
            colorCode.add(R.color.MediumOrchid);
            colorCode.add(R.color.DarkGoldenrod);
            colorCode.add(R.color.FireBrick);
            colorCode.add(R.color.PowderBlue);
            colorCode.add(R.color.LightSteelBlue);
            colorCode.add(R.color.PaleTurquoise);
            colorCode.add(R.color.GreenYellow);
            colorCode.add(R.color.LightBlue);
            colorCode.add(R.color.DarkGray);
            colorCode.add(R.color.Sienna);
            colorCode.add(R.color.YellowGreen);
            colorCode.add(R.color.DarkOrchid);
            colorCode.add(R.color.PaleGreen);
            colorCode.add(R.color.DarkViolet);
            colorCode.add(R.color.MediumPurple);
            colorCode.add(R.color.LightGreen);
            colorCode.add(R.color.DarkSeaGreen);
            colorCode.add(R.color.SaddleBrown);
            colorCode.add(R.color.DarkMagenta);
            colorCode.add(R.color.DarkRed);
            colorCode.add(R.color.BlueViolet);
            colorCode.add(R.color.LightSkyBlue);
            colorCode.add(R.color.Gray);
            colorCode.add(R.color.Olive);
            colorCode.add(R.color.Purple);
            colorCode.add(R.color.Aquamarine);
            colorCode.add(R.color.LawnGreen);
            colorCode.add(R.color.MediumSlateBlue);
            colorCode.add(R.color.SlateGray);
            colorCode.add(R.color.DarkOliveGreen);
            colorCode.add(R.color.Indigo);
            colorCode.add(R.color.MediumTurquoise);
            colorCode.add(R.color.DarkSlateBlue);
            colorCode.add(R.color.SteelBlue);
            colorCode.add(R.color.RoyalBlue);
            colorCode.add(R.color.MediumSeaGreen);
            colorCode.add(R.color.LimeGreen);
            colorCode.add(R.color.DarkSlateGray);
            colorCode.add(R.color.SeaGreen);
            colorCode.add(R.color.ForestGreen);
            colorCode.add(R.color.LightSeaGreen);
            colorCode.add(R.color.DodgerBlue);
            colorCode.add(R.color.Aqua);
            colorCode.add(R.color.SpringGreen);
            colorCode.add(R.color.MediumSpringGreen);
            colorCode.add(R.color.DeepSkyBlue);
            colorCode.add(R.color.DarkCyan);
            colorCode.add(R.color.Green);
            colorCode.add(R.color.DarkGreen);
            colorCode.add(R.color.Blue);
            colorCode.add(R.color.DarkBlue);
            colorCode.add(R.color.Navy);
            //colorCode.add(R.color.Black);
        } // Colors used

        Random randomColor= new Random();
        int number = randomColor.nextInt(colorCode.size());

        return colorCode.get(number);
    } // Random colors to notes

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (noteAdapter != null) {
            noteAdapter.stopListening();
        }
    }
}




