package com.piggest.mylovelynotepad.Services

import android.content.Context
import android.widget.Toast
import com.piggest.mylovelynotepad.Controller.App
import com.piggest.mylovelynotepad.Model.Note
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object DataService {

    var notes: MutableList<Note> = mutableListOf()

    fun getTextDir() : File {
        return File(App.instance.filesDir, "texts")
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy/MM/dd \n HH:mm")
        return format.format(date)
    }

    fun printToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).apply { show() }
    }

}