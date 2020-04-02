package com.mrwhoknows.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrwhoknows.notes.model.Note;
import com.mrwhoknows.notes.persistence.NoteRepository;
import com.mrwhoknows.notes.util.Utility;

public class NoteEditorActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    private static final String TAG = "NoteEditor";
    EditText editTitle, noteEditText;
    TextView viewTitle;
    ImageView backBtn, checkBtn;
    RelativeLayout backBtnContainer, checkBtnContainer;

    private Note initialNote;
    private Note finalNote;
    private boolean isNewNote;
    private GestureDetector gestureDetector;

    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;
    private int MODE;

    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        noteEditText = findViewById(R.id.noteText);
        editTitle = findViewById(R.id.noteEditTitle);
        viewTitle = findViewById(R.id.noteTextTitle);
        backBtn = findViewById(R.id.toolbar_back_arrow);
        checkBtn = findViewById(R.id.toolbar_check);
        backBtnContainer = findViewById(R.id.toolbar_back_arrow_container);
        checkBtnContainer = findViewById(R.id.toolbar_check_container);

        setListeners();
        noteRepository = new NoteRepository(this);

        if (getIncomingIntent()) {
//            For New Note (View)
            setNewNote();
            enableEditMode();
        } else {
//            For Existing Note (Edit)
            setNoteProperties();
            disableContentInteraction();
        }
    }

    private void saveChanges() {
        if (isNewNote) {
            saveNewNote();
        } else {

        }
    }

    private void saveNewNote() {
        noteRepository.insertNoteTask(finalNote);
    }

    public boolean getIncomingIntent() {
        if (getIntent().hasExtra("selected_note")) {
            initialNote = getIntent().getParcelableExtra("selected_note");
            finalNote = getIntent().getParcelableExtra("selected_note");

            MODE = EDIT_MODE_ENABLED;
            isNewNote = false;
            return false;
        }
        MODE = EDIT_MODE_ENABLED;
        isNewNote = true;
        return true;
    }

    private void setNewNote() {
        viewTitle.setText("Title");
        editTitle.setText("Title");

        initialNote = new Note();
        finalNote = new Note();
        initialNote.setTitle("Title");
        finalNote.setTitle("Title");

    }

    private void setNoteProperties() {
        viewTitle.setText(initialNote.getTitle());
        editTitle.setText(initialNote.getTitle());
        noteEditText.setText(initialNote.getContent());
    }

    private void setListeners() {
        noteEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
        viewTitle.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        editTitle.addTextChangedListener(this);
    }

    private void disableContentInteraction() {
        noteEditText.setKeyListener(null);
        noteEditText.setFocusable(false);
        noteEditText.setFocusableInTouchMode(false);
        noteEditText.setCursorVisible(false);
        noteEditText.clearFocus();
    }

    private void enableContentInteraction() {
        noteEditText.setKeyListener(new EditText(this).getKeyListener());
        noteEditText.setFocusable(true);
        noteEditText.setFocusableInTouchMode(true);
        noteEditText.setCursorVisible(true);
        noteEditText.requestFocus();
    }

    private void enableEditMode() {
        backBtnContainer.setVisibility(View.GONE);
        checkBtnContainer.setVisibility(View.VISIBLE);

        viewTitle.setVisibility(View.GONE);
        editTitle.setVisibility(View.VISIBLE);

        MODE = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode() {
        backBtnContainer.setVisibility(View.VISIBLE);
        checkBtnContainer.setVisibility(View.GONE);

        viewTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);

        MODE = EDIT_MODE_DISABLED;
        disableContentInteraction();

        String temp = noteEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");

        if (temp.length() > 0) {
            finalNote.setTitle(editTitle.getText().toString());
            finalNote.setContent(noteEditText.getText().toString());
            finalNote.setTimeStamp(Utility.getCurrentTimeStamp());

            if (!finalNote.getContent().equals(initialNote.getContent())
                    || !finalNote.getTitle().equals(initialNote.getTitle())) {
                saveChanges();
            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_check: {
                hideKeyboard();
                disableEditMode();
                break;
            }
            case R.id.noteTextTitle: {
                enableEditMode();
                editTitle.requestFocus();
                editTitle.setSelection(editTitle.length());
                break;
            }
            case R.id.toolbar_back_arrow: {
                finish();
                break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: tapped");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("mode", MODE);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MODE = savedInstanceState.getInt("mode");
        if (MODE == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        viewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
