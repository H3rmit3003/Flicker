package com.example.flicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activateToolber(true)
    }
}