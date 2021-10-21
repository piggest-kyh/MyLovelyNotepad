package com.piggest.mylovelynotepad.Controller

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.piggest.mylovelynotepad.R
import com.piggest.mylovelynotepad.Services.DataService

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
    lateinit var deleteButton: ImageButton
    lateinit var noteDate: TextView
    lateinit var fullText: EditText
    lateinit var textDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       isNewNote =  intent.getBooleanExtra("newNote", true)

        noteName = intent.getStringExtra("noteName").toString()
        noteText = intent.getStringExtra("noteText").toString()
        modifiedDate = intent.getStringExtra("modifiedDate").toString()

        setContentView(R.layout.activity_note)
        backButton = findViewById(R.id.back_button)
        saveButton = findViewById(R.id.save_button)
        noteDate = findViewById(R.id.note_activity_date)
        fullText = findViewById(R.id.full_text_note)
        deleteButton = findViewById(R.id.delete_button)

        setUpNoteDataField()

        textDir = DataService.getTextDir()

        saveButton.setOnClickListener{
            saveNote()
        }

        deleteButton.setOnClickListener {
            deleteNote()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun deleteNote() {
        if (isNewNote){
            finish()
        } else {
            if (noteName != ""){
                val textFile = File(textDir, noteName)
                textFile.delete()
                finish()
            } else {
                DataService.printToast(this, "Cannot find File name properly")
            }
        }
    }

    fun setUpNoteDataField() {
        if(isNewNote){
            noteDate.visibility = View.INVISIBLE
            fullText.setText("")
        } else {
            noteDate.text = modifiedDate
            noteDate.visibility = View.VISIBLE
            fullText.setText(noteText)
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
                DataService.printToast(this, "Cannot find File name properly")
            }
        }
    }



    fun writeFile(withName: String) {
        val noteText = fullText.text.toString()
        val textFile = File(textDir, withName)
        val writer = PrintWriter(textFile)
        try {
            writer.write(noteText)
            writer.close()
            DataService.printToast(this, "File saved successfully")
            finish()
        } catch (e: Exception) {
            print(e.localizedMessage)
        }
    }
}