package com.example.flicker

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context: Context, recyclerView:RecyclerView, private val listener:OnRecyclerItemClickListener)
           : RecyclerView.SimpleOnItemTouchListener(){

    private val tag:String = "RecyclerListener"
    interface OnRecyclerItemClickListener{
        fun onItemClickListener(view: View, position:Int)
        fun onItemLongClickListener(view:View,position:Int)
    }

    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val childView = recyclerView.findChildViewUnder(e.x,e.y)
            listener.onItemClickListener(childView!!,recyclerView.getChildAdapterPosition(childView!!))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val childView = recyclerView.findChildViewUnder(e.x,e.y)
            listener.onItemLongClickListener(childView!!,recyclerView.getChildAdapterPosition(childView!!))
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(e)
    }
}