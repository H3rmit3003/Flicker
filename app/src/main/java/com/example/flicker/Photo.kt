package com.example.flicker

import android.util.Log
import java.io.IOException
import java.io.Serializable

class Photo(val title:String,val author:String,val authorId:String,val link:String,val tag:String,val image:String):Serializable {

    companion object{
        private const val serialVersionUID = 1L
    }
    override fun toString(): String {
        return "Photo(title=$title,author=$author,authorId=$authorId,link=$link,tag=$tag,image=$image)"
    }
    @Throws(IOException::class)
    private fun writeObject(out: java.io.ObjectOutputStream){
        title
    }
}