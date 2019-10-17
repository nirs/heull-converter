package com.huellbabineaux.huellconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

const val HOURS_PER_HUELL = 1.71666667

class MainActivity : AppCompatActivity() {

    private lateinit var huells: EditText
    private lateinit var hours : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        huells = findViewById(R.id.huells_text)
        hours = findViewById(R.id.hours_text)

        huells.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == huells) updateHours(e.toString())
            }
        })

        hours.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == hours) updateHuells(e.toString())
            }
        })

    }

    fun updateHuells(hoursValue: String) {
        if (hoursValue.isEmpty())
            huells.setText("")
        else
            huells.setText(formatTime(hoursValue.toDouble() / HOURS_PER_HUELL))
    }

    fun updateHours(huellsValue: String) {
        if (huellsValue == "")
            hours.setText("")
        else
            hours.setText(formatTime(huellsValue.toDouble() * HOURS_PER_HUELL))
    }

    fun formatTime(value : Double) : String {
        return String.format("%.3f", value)
    }

}

abstract class Watcher: TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}