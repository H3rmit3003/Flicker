package com.example.flicker

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),GetRawData.OnDownloadComplete,GetFlickrJsonData.OnDataAvailable,RecyclerItemClickListener.OnRecyclerItemClickListener {
    private val TAG = "MainActivity"
    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClickListener(view: View, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClickListener(view: View, position: Int) {
        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if(photo != null){
            val intent  = Intent(this@MainaAtivity,PhotodetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER,photo)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activateToolber(false)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = flickrRecyclerViewAdapter
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,recycler_view,this))

        val url = createURL("https://www.flickr.com/services/feeds/photos_public.gne","","en-us",true)
        val getRawData = GetRawData(this)
        getRawData.execute(url)
    }

    private fun createURL(baseURL:String,searchCriteria:String,lang:String,matchALL:Boolean):String{
        return  Uri.parse(baseURL) .buildUpon().appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",if(matchALL) "ALL" else "ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString()
    }

    override fun onDownloadComplete(data:String,status:DownloadStatus){
        if(status == DownloadStatus.OK){
            Log.d(TAG,"onDownloadComplete called data: $data")
            val getFlickrJsonData = GetFlickrJsonData(this)
            getFlickrJsonData.execute(data)
        } else {
            Log.e(TAG,"Download failed with $status. Error : $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG,"onDataAvailable, data is $data")
        flickrRecyclerViewAdapter.loadNewData(data)
    }

    override fun onError(exception: Exception) {
        Log.e(TAG,"OnError called! ${exception.message}")
    }
}