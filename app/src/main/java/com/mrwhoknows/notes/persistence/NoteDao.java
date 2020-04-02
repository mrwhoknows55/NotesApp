package com.mrwhoknows.notes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mrwhoknows.notes.model.Note;

import java.util.List;
@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM notes WHERE title LIKE :title ")
    LiveData<List<Note>> getCustomQueryNotes(String title);

    @Delete
    int deleteNotes(Note... notes);

    @Update
    int updateNotes(Note... notes);

}
