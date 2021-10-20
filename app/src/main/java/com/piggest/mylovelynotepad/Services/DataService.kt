package com.piggest.mylovelynotepad.Services

import com.piggest.mylovelynotepad.Controller.App
import com.piggest.mylovelynotepad.Model.Note
import java.io.File

object DataService {

    var notes: MutableList<Note> = mutableListOf()

    fun getTextDir() : File {
        return File(App.instance.filesDir, "texts")
    }

}