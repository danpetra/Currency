package com.example.currency.ui

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView

class OnUserSelectListener (
    private val onClick: OnClick
): AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private var userSelect = false
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        userSelect = true
        return false
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (userSelect){
                onClick.onClick(p0,p1,p2,p3)
            Log.i("Spinner","On select")
            userSelect = false
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    interface OnClick{
        fun onClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
    }
}

