package com.piggest.mylovelynotepad.Controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.piggest.mylovelynotepad.Adapters.NoteAdapter
import com.piggest.mylovelynotepad.Model.Note
import com.piggest.mylovelynotepad.R
import com.piggest.mylovelynotepad.Services.DataService
import java.io.File
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

//    val note1: Note = Note("first Note", "2021/10/19 \n 18.43", "1")
//    val note2: Note = Note("Second Note", "2021/10/19 \n 18.47", "2")
//    val fakeNotes  = listOf<Note>(note1, note2)

    lateinit var adapter: NoteAdapter
    lateinit var textDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            Intent(this, NoteActivity :: class.java)
                .putExtra("NewNote", true)
                .apply { startActivity(this) }
        }

         textDir = DataService.getTextDir()

        getTextFilesFromDir(checkIfDirectoryExistsAndCreate(textDir))

        adapter = NoteAdapter(this, DataService.notes)
        val notesListView = findViewById<RecyclerView>(R.id.notes_list_view)
        notesListView.adapter = adapter
        notesListView.layoutManager = LinearLayoutManager(this)
        notesListView.setHasFixedSize(true)
    }


    fun checkIfDirectoryExistsAndCreate(dir: File) : Boolean{
        if(!dir.exists()){
            dir.mkdir()
            return false
        }
        return true
    }

    fun getTextFilesFromDir(dirExist: Boolean) {
        if(dirExist){
           var noteFilesList = textDir.listFiles()

            noteFilesList.forEach {
                try {
                    val fileName = it.name
                    val scanner = Scanner(it)
                    val fileText = scanner.next()
                    val fileModifiedDate = it.lastModified().toString()

                    val newNote = Note(fileText, fileModifiedDate, fileName)
                    DataService.notes.add(newNote)
                } catch (e: Exception) {
                    print(e.localizedMessage)
                }
            }
        }
    }
}