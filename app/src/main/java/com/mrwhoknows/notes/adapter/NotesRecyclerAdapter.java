package com.mrwhoknows.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrwhoknows.notes.R;
import com.mrwhoknows.notes.model.Note;
import com.mrwhoknows.notes.util.Utility;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>  {

    private ArrayList<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;


    public NotesRecyclerAdapter(ArrayList<Note> notes,  OnNoteClickListener onNoteClickListener) {
        this.notes = notes;
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, onNoteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getTitle());
        holder.timeStamp.setText(Utility.getCurrentTimeStamp());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, timeStamp;
        private OnNoteClickListener onNoteClickListener;

        public ViewHolder(@NonNull View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            timeStamp = itemView.findViewById(R.id.time);
            this.onNoteClickListener = onNoteClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteClickListener{
        void onNoteClick(int position);
    }

}