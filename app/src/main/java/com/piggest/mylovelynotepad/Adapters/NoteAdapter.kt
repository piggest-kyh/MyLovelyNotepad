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


//custom adapter for showing notes in list
class NoteAdapter(val context: Context,val notes: List<Note>, val itemClick: (Note)->Unit): Adapter<NoteAdapter.NoteHolder>() {


    //inner object which binding ui element of current row with information about note
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

            //added click listener for row with help of lambda expression because recycler view don have inbuilt click listener
            itemView.setOnClickListener{itemClick(note)}
        }
    }

    //when viewHolder created xml layout file can be invoked
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.note_item_layout, parent, false)
        return NoteHolder(view, itemClick)
    }

    //binding exact note to current note position
    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindProduct(notes[position], context)
    }

    //return number of row in list
    override fun getItemCount(): Int {
        return notes.count()
    }
}