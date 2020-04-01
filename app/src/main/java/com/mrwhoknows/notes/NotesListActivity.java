package com.mrwhoknows.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mrwhoknows.notes.adapter.NotesRecyclerAdapter;
import com.mrwhoknows.notes.model.Note;
import com.mrwhoknows.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements
        NotesRecyclerAdapter.OnNoteClickListener,
        View.OnClickListener {
    private static final String TAG = "NotesListActivity";

    private ArrayList<Note> notes = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        findViewById(R.id.fab_add).setOnClickListener(this);
        setSupportActionBar((Toolbar) findViewById(R.id.notesToolbar));
        setTitle("Notes App");

        for (int i = 0; i < 1000; i++) {
            notes.add(new Note("Title: " + i, "Content", "31 March"));
        }

        recyclerViewConfig();
    }

    private void recyclerViewConfig() {
        recyclerView = findViewById(R.id.notesRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NotesRecyclerAdapter(notes, this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(24);

        recyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("selected_note", notes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note  note) {
        this.notes.remove(note);
        adapter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(notes.get(viewHolder.getAdapterPosition()));
        }
    };
}
