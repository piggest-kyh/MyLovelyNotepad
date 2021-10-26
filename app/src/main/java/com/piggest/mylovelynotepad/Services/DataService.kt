package com.piggest.mylovelynotepad.Services

import android.content.Context
import android.widget.Toast
import com.piggest.mylovelynotepad.Controller.App
import com.piggest.mylovelynotepad.Model.Note
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object DataService {

    //Data Service is singleton class and is used for keeping info and common func for multiply classes

    //Notes array is array which information about all existence notes
    var notes: MutableList<Note> = mutableListOf()

    //func return file dir for text on internal storage
    fun getTextDir() : File {
        return File(App.instance.filesDir, "texts")
    }

    //converting date in long formal to readable date
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy/MM/dd \n HH:mm")
        return format.format(date)
    }

    //little helper for showing message easily
    fun printToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).apply { show() }
    }
}