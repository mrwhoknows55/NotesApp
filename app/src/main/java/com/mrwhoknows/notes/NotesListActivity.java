package com.mrwhoknows.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mrwhoknows.notes.adapter.NotesRecyclerAdapter;
import com.mrwhoknows.notes.model.Note;
import com.mrwhoknows.notes.persistence.NoteRepository;
import com.mrwhoknows.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity implements
        NotesRecyclerAdapter.OnNoteClickListener,
        View.OnClickListener {
    private static final String TAG = "NotesListActivity";

    private ArrayList<Note> mNotes = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        findViewById(R.id.fab_add).setOnClickListener(this);
        setSupportActionBar((Toolbar) findViewById(R.id.notesToolbar));
        setTitle("Notes App");
        recyclerViewConfig();

//        insertFakeNotes();
        retrieveNotes();
        Log.d(TAG, "doInBackground: " + Thread.currentThread().getName() );

    }


    private void retrieveNotes() {
        noteRepository = new NoteRepository(this);
        noteRepository.retrieveDataTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (mNotes.size() > 0) {
                    mNotes.clear();
                }
                if (notes != null) {
                    mNotes.addAll(notes);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void insertFakeNotes() {
        for (int i = 0; i < 1000; i++) {
            mNotes.add(new Note("Title: " + i, "Content", "31 March"));
        }
        adapter.notifyDataSetChanged();

    }

    private void recyclerViewConfig() {
        recyclerView = findViewById(R.id.notesRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new NotesRecyclerAdapter(mNotes, this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(24);

        recyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note) {
        this.mNotes.remove(note);
        adapter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
