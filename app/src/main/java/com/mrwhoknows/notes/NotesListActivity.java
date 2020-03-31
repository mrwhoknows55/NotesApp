package com.mrwhoknows.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mrwhoknows.notes.adapter.NotesRecyclerAdapter;
import com.mrwhoknows.notes.model.Note;
import com.mrwhoknows.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteClickListener {

    private static final String TAG = "NotesListActivity";

    private ArrayList<Note> notes = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteslist);

        setSupportActionBar((Toolbar) findViewById(R.id.notesToolbar));
        setTitle("Notes App");

        recyclerView = findViewById(R.id.notesRecyclerView);

        for (int i = 0; i < 1000; i++) {
            notes.add(new Note("Title: " + i, "Content", "31 March"));
        }

        layoutManager = new LinearLayoutManager(this);
        adapter = new NotesRecyclerAdapter(notes, this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(24);
        recyclerView.addItemDecoration(verticalSpacingItemDecorator);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("selected_note", notes.get(position));
        startActivity(intent);
    }
}
