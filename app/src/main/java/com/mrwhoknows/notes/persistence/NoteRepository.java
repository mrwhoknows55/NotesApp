package com.mrwhoknows.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.mrwhoknows.notes.async.InsertAsyncTask;
import com.mrwhoknows.notes.model.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase noteDatabase;

    public NoteRepository(Context context) {
        this.noteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note) {
        new InsertAsyncTask(noteDatabase.getNoteDao()).execute(note);
    }

    public void updateNoteTask(Note note) {
    }

    public void deleteNoteTask(Note note) {
    }

    public LiveData<List<Note>> retrieveDataTask() {
        return noteDatabase.getNoteDao().getNotes();
    }
}
