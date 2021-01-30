package com.example.flicker

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

internal const val FLICKR_QUERY = "FLICKR_QUERY"
internal const val PHOTO_TRANSFER = "PHOTO_TRANSFER"

open class BaseActivity:AppCompatActivity() {
    private val tag = "BaseActivity"
    internal fun activateToolber(enableHome:Boolean){
        Log.d(tag,".activateToolbar")
        val toolBar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}