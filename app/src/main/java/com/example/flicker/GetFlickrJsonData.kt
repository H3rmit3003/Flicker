package com.example.flicker

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class GetFlickrJsonData(private val listener:OnDataAvailable):AsyncTask<String,Void,ArrayList<Photo>>() {
    private val tag:String = "GetFlickrJsonData"
    interface OnDataAvailable{
        fun onDataAvailable(data:List<Photo>)
        fun onError(exception:Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(tag,"doInBackground starts!")
        val photoList = ArrayList<Photo>()
        try{
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")
            for (i in 0 until itemsArray.length()){
                val jsonPhoto = itemsArray.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId= jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")
                val jsonMedia = jsonPhoto.getJSONObject("media")
                val photoUrl = jsonMedia.getString("m")
                val link = photoUrl.replaceFirst("_m.jpg","_b.jpg")
                val photoObject = Photo(title,author,authorId,link,tags,photoUrl)
                photoList.add(photoObject)
                Log.d(tag,"doInBackground: $photoObject")
            }
        } catch (e:JSONException){
            e.printStackTrace()
            Log.e(tag,"doInBackground: Error processing JSON Data!")
            cancel(true)
            listener.onError(e)
        }
        Log.d(tag,"doInBackground Ends")
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(tag,".onPostExecute ends!")
    }
}