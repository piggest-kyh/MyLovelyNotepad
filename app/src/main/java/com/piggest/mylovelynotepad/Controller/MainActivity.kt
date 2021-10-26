package com.piggest.mylovelynotepad.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    //init adapter and file directory
    private lateinit var adapter: NoteAdapter
    private lateinit var textDir: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //floating action bar Section
        // bind button for create new note and add function with init new screen for new note
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            Intent(this, NoteActivity::class.java)
                .putExtra("newNote", true)
                .apply { startActivity(this) }
        }

        // init directory with text files with help of separate func in DataService. Use Separate class because this file is used multiply times in two activities
        textDir = DataService.getTextDir()

        //check if directory exist and if it is, find all files which keeping inside of directory
        getTextFilesFromDir(checkIfDirectoryExistsAndCreate(textDir))

        //create an adapter for RecyclerView list and adding on item click lambda expression for complete intent in case of click on specific row
        // with additional information about item
        
        adapter = NoteAdapter(this, DataService.notes) {
            Intent(this, NoteActivity::class.java)
                .putExtra("newNote", false)
                .putExtra("noteName", it.id)
                .putExtra("noteText", it.noteText)
                .putExtra("modifiedDate", it.noteDate)
                .apply { startActivity(this) }
        }

        //binding RecyclerListView to adapter
        val notesListView = findViewById<RecyclerView>(R.id.notes_list_view)
        notesListView.adapter = adapter
        notesListView.layoutManager = LinearLayoutManager(this)

        DataService.printToast(this, "Hello world")
    }

    override fun onResume() {
        super.onResume()

        //When list with notes is showed again, all dataset is cleaned and then all information about notes loads again
        DataService.notes.clear()
        getTextFilesFromDir(checkIfDirectoryExistsAndCreate(textDir))
        adapter.notifyDataSetChanged()
    }

    //Check if directory for notes exist and return true if exists, and return false and create directory if not
    private fun checkIfDirectoryExistsAndCreate(dir: File): Boolean {
        if (!dir.exists()) {
            dir.mkdir()
            return false
        }
        return true
    }

    //Check if directory for notes exist and if exists try to load all files from directory an adding it to array in DataService
    private fun getTextFilesFromDir(dirExist: Boolean) {
        if (dirExist) {

            //trying to find all files and then get files name, modify date and text for creating note object
            textDir.listFiles()?.forEach {
                try {
                    val fileName = it.name
                    print(fileName)
                    val scanner = Scanner(it)
                    val sb: StringBuilder = StringBuilder("")
                    while (scanner.hasNextLine()) {
                        sb.append(scanner.nextLine() + "\n")
                    }
                    sb.setLength(sb.length - 1)
                    val fileText = sb.toString()
                    val fileModifiedDate = DataService.convertLongToTime(it.lastModified())
                    val newNote = Note(fileText, fileModifiedDate, fileName)
                    DataService.notes.add(newNote)
                } catch (e: Exception) {
                    if (e.localizedMessage != null) {
                        DataService.printToast(this, e.localizedMessage!!)
                    } else {
                        DataService.printToast(this, e.toString())
                    }
                }
            }
            //Sorting all notes in list by date
            DataService.notes.sortWith(compareByDescending { it.noteDate })
        }
    }
}