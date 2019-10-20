package com.huellbabineaux.huellconverter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val LOG_KEY = "HUEL_CONVERTER"

class MainActivity : AppCompatActivity() {

    private lateinit var huellsText: EditText
    private lateinit var intervalText : EditText
    private lateinit var intervalLabel : TextView

    private lateinit var displayUnit: Units
    private lateinit var interval: Interval

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        huellsText = findViewById(R.id.huells_text)
        intervalText = findViewById(R.id.interval_text)
        intervalLabel = findViewById(R.id.interval_label)

        huellsText.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == huellsText) {
                    changeInterval(e.toString(), Units.HUELLS)
                    updateIntervalText()
                }
            }
        })

        intervalText.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == intervalText) {
                    changeInterval(e.toString(), displayUnit)
                    updateHuellsText()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()

        val pref = getPreferences()
        displayUnit = pref.displayUnit
        interval = pref.interval

        intervalLabel.text = displayUnit.name
        intervalText.requestFocus()
        updateIntervalText()
    }

    override fun onStop() {
        super.onStop()

        val pref = getPreferences()
        pref.displayUnit = displayUnit
        pref.interval = interval
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

    private fun changeDisplayUnit(unit: Units) {
        displayUnit = unit
        intervalLabel.text = unit.name
        updateIntervalText()
    }

    private fun changeInterval(text: String, unit: Units) {
        interval = when (text) {
            "" -> Interval(0.0)
            else -> Interval(text, unit)
        }
    }

    private fun updateIntervalText() {
        intervalText.setText(formatTime(interval.toUnit(displayUnit)))
    }

    private fun updateHuellsText() {
        huellsText.setText(formatTime(interval.toUnit(Units.HUELLS)))
    }

    private fun formatTime(value : Double) : String {
        return String.format("%.3f", value)
    }

    private fun getPreferences() : Preferences {
        return Preferences(getPreferences(Context.MODE_PRIVATE))
    }
}

abstract class Watcher: TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}