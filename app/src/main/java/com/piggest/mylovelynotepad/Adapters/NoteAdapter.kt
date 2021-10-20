package com.piggest.mylovelynotepad.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.piggest.mylovelynotepad.Model.Note
import com.piggest.mylovelynotepad.R

class NoteAdapter(val context: Context,val notes: List<Note>): Adapter<NoteAdapter.NoteHolder>() {

    inner  class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteHeadline = itemView.findViewById<TextView>(R.id.note_headline)
        val noteTime = itemView.findViewById<TextView>(R.id.note_time)

        fun bindProduct(note: Note) {
            noteHeadline.text = note.noteText
            noteTime.text = note.noteDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.note_item_layout, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindProduct(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.count()
    }
}