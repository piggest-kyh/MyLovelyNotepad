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

class NoteAdapter(val context: Context,val notes: List<Note>, val itemClick: (Note)->Unit): Adapter<NoteAdapter.NoteHolder>() {

    inner  class NoteHolder(itemView: View, val itemClick: (Note) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val noteHeadline = itemView.findViewById<TextView>(R.id.note_headline)
        val noteTime = itemView.findViewById<TextView>(R.id.note_time)

        fun bindProduct(note: Note, context: Context) {

            val text = note.noteText
            val headline = if (text.length > 31){
                text.subSequence(0, 30).toString()
            } else {
                text
            }

            noteHeadline.text = "$headline.."
            noteTime.text = note.noteDate
            itemView.setOnClickListener{itemClick(note)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.note_item_layout, parent, false)
        return NoteHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindProduct(notes[position], context)
    }

    override fun getItemCount(): Int {
        return notes.count()
    }
}