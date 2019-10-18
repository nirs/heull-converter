package com.huellbabineaux.huellconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView

const val HOURS_PER_HUELL = 1.71666667

interface Time {
    fun hours() : Double
    fun minutes() : Double
    fun seconds() : Double
    fun huells() : Double
}

enum class Units : Time {
    HOURS {
        override fun hours() : Double = 1.0
        override fun minutes() : Double = 60.0
        override fun seconds() : Double = 3600.0
        override fun huells() : Double = hours() / HOURS_PER_HUELL
    },
    MINUTES {
        override fun hours() : Double = 1.0 / 60.0
        override fun minutes() : Double = 1.0
        override fun seconds() : Double = 60.0
        override fun huells() : Double = hours() / HOURS_PER_HUELL
    },
    SECONDS {
        override fun hours() : Double = 1.0 / 3600.0
        override fun minutes() : Double = 1.0 / 60.0
        override fun seconds() : Double = 1.0
        override fun huells() : Double = hours() / HOURS_PER_HUELL
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var huells: EditText
    private lateinit var hours : EditText
    private lateinit var label : TextView

    private var currentUnit = Units.HOURS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        huells = findViewById(R.id.huells_text)
        hours = findViewById(R.id.hours_text)
        label = findViewById(R.id.hours_label)

        huells.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == huells) updateHours()
            }
        })

        hours.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == hours) updateHuells()
            }
        })

        hours.requestFocus()
        hours.setText(formatTime(HOURS_PER_HUELL))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.hours -> {
                changeUnit(Units.HOURS)
                true
            }
            R.id.minutes -> {
                changeUnit(Units.MINUTES)
                true
            }
            R.id.seconds -> {
                changeUnit(Units.SECONDS)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun changeUnit(newUnit: Units) {
        val text = hours.text.toString()
        val oldUnit = currentUnit

        currentUnit = newUnit
        label.setText(newUnit.toString())

        if (!text.isEmpty()) {
            val currentValue = text.toDouble()
            val newValue = when (newUnit) {
                Units.HOURS -> {currentValue * oldUnit.hours()}
                Units.MINUTES -> {currentValue * oldUnit.minutes()}
                Units.SECONDS -> {currentValue * oldUnit.seconds()}
            }
            hours.setText(formatTime(newValue))
        }
    }

    fun updateHuells() {
        val text = hours.text.toString()
        if (text.isEmpty())
            huells.setText("")
        else {
            val newValue = text.toDouble() * currentUnit.huells()
            huells.setText(formatTime(newValue))
        }
    }

    fun updateHours() {
        val text = huells.text.toString()
        if (text == "")
            hours.setText("")
        else {
            val newValue = text.toDouble() / currentUnit.huells()
            hours.setText(formatTime(newValue))
        }
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