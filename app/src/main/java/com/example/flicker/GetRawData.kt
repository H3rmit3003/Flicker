package com.example.flicker

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus{
    OK,IDLE,NOT_INITIALISED,FAILED_OR_EMPTY,PERMISSIONS_ERROR,ERROR
}
class GetRawData(private val listener: MainActivity) : AsyncTask<String,Void,String>(){
    private val tag:String = "GetRawDATA"
    private var downloadStatus = DownloadStatus.IDLE

    interface OnDownloadComplete{
        fun onDownloadComplete(data:String,status:DownloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if(params[0]==null){
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "No URL specified!"
        }
        try{
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e:Exception){
            val message:String = when(e){
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALISED
                    "doInBackground: Invalid URL ${e.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IO Exception reading data: ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackground: Security Exception, needs permission?: ${e.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "doInBackground: Unknown Error!"
                }
            }
            Log.e(tag,message)
            return message
        }
    }

    override fun onPostExecute(result: String) {
        Log.d(tag,"called parameter is $result")
        listener.onDownloadComplete(result,downloadStatus)
    }
}