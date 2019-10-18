package com.huellbabineaux.huellconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.huellbabineaux.huellconverter.time.secondsFrom
import com.huellbabineaux.huellconverter.time.secondsTo
import com.huellbabineaux.huellconverter.time.Units

class MainActivity : AppCompatActivity() {

    private lateinit var huellsText: EditText
    private lateinit var intervalText : EditText
    private lateinit var intervalLabel : TextView

    private var displayUnit = Units.HOURS
    private var seconds = secondsFrom(1.0, Units.HUELLS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        huellsText = findViewById(R.id.huells_text)
        intervalText = findViewById(R.id.interval_text)
        intervalLabel = findViewById(R.id.interval_label)

        huellsText.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == huellsText) {
                    changeSeconds(e.toString(), Units.HUELLS)
                    updateIntervalText()
                }
            }
        })

        intervalText.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == intervalText) {
                    changeSeconds(e.toString(), displayUnit)
                    updateHuellsText()
                }
            }
        })

        intervalText.requestFocus()
        updateIntervalText()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.hours -> {
                changeDisplayUnit(Units.HOURS)
                true
            }
            R.id.minutes -> {
                changeDisplayUnit(Units.MINUTES)
                true
            }
            R.id.seconds -> {
                changeDisplayUnit(Units.SECONDS)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun changeDisplayUnit(unit: Units) {
        displayUnit = unit
        intervalLabel.setText(unit.toString())
        updateIntervalText()
    }

    fun changeSeconds(text: String, unit: Units) {
        seconds = when (text) {
            "" -> 0.0
            else -> secondsFrom(text.toDouble(), unit)
        }
    }

    fun updateIntervalText() {
        intervalText.setText(formatTime(secondsTo(seconds, displayUnit)))
    }

    fun updateHuellsText() {
        huellsText.setText(formatTime(secondsTo(seconds, Units.HUELLS)))
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