package com.mrwhoknows.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.mrwhoknows.notes.adapter.NotesRecyclerAdapter;
import com.mrwhoknows.notes.model.Note;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity {

    private static final String TAG = "NotesListActivity";

    private ArrayList<Note> notes = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteslist);

        recyclerView = findViewById(R.id.notesRecyclerView);
        Note note = new Note();

        for (int i = 0; i < 1000; i++) {
            notes.add(new Note("Title: " + i, "Content", "31 March"));
        }

        layoutManager = new LinearLayoutManager(this);
        adapter = new NotesRecyclerAdapter(notes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
