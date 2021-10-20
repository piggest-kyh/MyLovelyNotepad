package com.piggest.mylovelynotepad.Controller

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.piggest.mylovelynotepad.R
import com.piggest.mylovelynotepad.Services.DataService

import com.piggest.mylovelynotepad.databinding.ActivityNoteBinding
import java.io.File
import java.io.PrintWriter
import java.util.*

class NoteActivity : AppCompatActivity() {

    var isNewNote: Boolean = true;
    var noteName: String = ""
    var noteText: String = ""
    var modifiedDate: String = ""


    lateinit var backButton: ImageButton
    lateinit var saveButton: Button
    lateinit var noteDate: TextView
    lateinit var fullText: EditText
    lateinit var textDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       isNewNote =  intent.getBooleanExtra("NewNote", true)

        noteName = intent.getStringExtra("noteName").toString()
        noteText = intent.getStringExtra("noteText").toString()
        modifiedDate = intent.getStringExtra("modifiedDate").toString()

        setContentView(R.layout.activity_note)
        backButton = findViewById<ImageButton>(R.id.back_button)
        saveButton = findViewById<Button>(R.id.save_button)
        noteDate = findViewById(R.id.note_activity_date)
        fullText = findViewById(R.id.full_text_note)

        setUpDateField()

        textDir = DataService.getTextDir()

        saveButton.setOnClickListener{
            saveNote()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    fun setUpDateField() {
        if(isNewNote){
            noteDate.visibility = View.INVISIBLE
        } else {
            noteDate.text = modifiedDate
            noteDate.visibility = View.VISIBLE
        }
    }

    fun saveNote() {
        if (isNewNote){
            val noteRandomName = UUID.randomUUID().toString()
            writeFile(noteRandomName)
        } else {
            if (noteName != ""){
                writeFile(noteName)
            } else {
                TODO("add error disc")
            }
        }
    }

    fun writeFile(withName: String) {
        val noteText = fullText.text.toString()
        val textFile = File(textDir, "$withName.txt")
        val writer = PrintWriter(textFile)
        try {
            writer.write(noteText)
            writer.close()
            finish()
        } catch (e: Exception) {
            print(e.localizedMessage)
        }
    }
}