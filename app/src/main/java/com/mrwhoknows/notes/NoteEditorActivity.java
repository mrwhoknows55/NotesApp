package com.mrwhoknows.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrwhoknows.notes.model.Note;

public class NoteEditorActivity extends AppCompatActivity {

    EditText noteTitleEdit, noteEdit;
    TextView noteTitle;
    ImageView backBtn, checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        noteEdit = findViewById(R.id.noteText);
        noteTitleEdit = findViewById(R.id.noteEditTitle);
        noteTitle = findViewById(R.id.noteTextTitle);

        if (getIntent().hasExtra("selected_note")){
            Note note = getIntent().getParcelableExtra("selected_note");
            noteTitle.setText(note.getTitle().toString());
            noteEdit.setText(note.getContent());
        }

    }
}
