package com.mrwhoknows.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.mrwhoknows.notes.model.Note;
import com.mrwhoknows.notes.persistence.NoteDao;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
    private static final String TAG = "InsertAsyncTask";
    private NoteDao noteDao;

    public InsertAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: " + Thread.currentThread().getName() );
        this.noteDao.insertNotes(notes);
        return null;
    }

}
