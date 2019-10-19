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
import com.huellbabineaux.huellconverter.time.Units
import com.huellbabineaux.huellconverter.time.secondsFrom
import com.huellbabineaux.huellconverter.time.secondsTo

val LOG_KEY = "HUEL_CONVERTER"

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
    }

    override fun onStart() {
        super.onStart()

        val pref = getPreferences()
        pref.load()
        displayUnit = pref.displayUnit
        seconds = pref.seconds

        intervalLabel.setText(displayUnit.name)
        intervalText.requestFocus()
        updateIntervalText()
    }

    override fun onStop() {
        super.onStop()
        getPreferences().save()
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
        intervalLabel.setText(unit.name)
        updateIntervalText()
    }

    private fun changeSeconds(text: String, unit: Units) {
        seconds = when (text) {
            "" -> 0.0
            else -> secondsFrom(text.toDouble(), unit)
        }
    }

    private fun updateIntervalText() {
        intervalText.setText(formatTime(secondsTo(seconds, displayUnit)))
    }

    private fun updateHuellsText() {
        huellsText.setText(formatTime(secondsTo(seconds, Units.HUELLS)))
    }

    private fun formatTime(value : Double) : String {
        return String.format("%.3f", value)
    }

    private fun getPreferences() : Preferences {
        val pref = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        return Preferences(pref, displayUnit, seconds)
    }

}

abstract class Watcher: TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}