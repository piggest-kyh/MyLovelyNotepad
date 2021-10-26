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


    //init val block: define buttons and variable for keepeing information about note
    var isNewNote: Boolean = true;
    var noteName: String = ""
    var noteText: String = ""
    var modifiedDate: String = ""


    lateinit var backButton: ImageButton
    lateinit var saveButton: ImageButton
    lateinit var deleteButton: ImageButton
    lateinit var noteDate: TextView
    lateinit var fullText: EditText
    lateinit var textDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //getting extra info about note from previous activity
       isNewNote =  intent.getBooleanExtra("newNote", true)

        noteName = intent.getStringExtra("noteName").toString()
        noteText = intent.getStringExtra("noteText").toString()
        modifiedDate = intent.getStringExtra("modifiedDate").toString()

        //binding all UI element from xml to variables
        setContentView(R.layout.activity_note)
        backButton = findViewById(R.id.back_button)
        saveButton = findViewById(R.id.save_button)
        noteDate = findViewById(R.id.note_activity_date)
        fullText = findViewById(R.id.full_text_note)
        deleteButton = findViewById(R.id.delete_button)

        //init func which define if it new note or not and setting up information accordingly
        setUpNoteDataField()

        //get info about app text dir with help of DataService
        textDir = DataService.getTextDir()

        //Set up all buttons click  listener with external function
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


    // if new note just make note date invisible otherwise setting up all info abote note from extra
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

    // save note func define if it is new note. If new note func give unic name to new note generated randomly otherwise func uses currient name for note to override existed note
    //func uses external func write file which fixes all write file operation
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
 // delete note with help of unic name of note then finish activity
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
}