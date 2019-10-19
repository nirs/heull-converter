package com.huellbabineaux.huellconverter

import android.content.SharedPreferences
import android.util.Log
import com.huellbabineaux.huellconverter.time.Units
import java.lang.ClassCastException
import java.lang.IllegalArgumentException

const val DISPLAY_UNIT = "displayUnit"
const val SECONDS = "seconds"

class Preferences(
    private val pref: SharedPreferences,
    var displayUnit: Units,
    var seconds: Double) {

    fun load() {
        var value : String?

        value = getString(DISPLAY_UNIT)
        if (value != null) {
            try {
                displayUnit = Units.valueOf(value)
                Log.d(LOG_KEY, "Loaded preferences %s=%s".format(DISPLAY_UNIT, displayUnit))
            } catch (e: IllegalArgumentException) {
                Log.w(LOG_KEY, "Invalid %s value: %s".format(DISPLAY_UNIT, e))
            }
        }

        value = getString(SECONDS)
        if (value != null) {
            try {
                seconds = value.toDouble()
                Log.d(LOG_KEY, "Loaded preferences %s=%s".format(SECONDS, seconds))
            } catch (e: NumberFormatException) {
                Log.w(LOG_KEY, "Invalid %s value: %s".format(SECONDS, e))
            }
        }
    }

    fun save() {
        Log.d(LOG_KEY, "Saving preferences %s=%s %s=%s"
            .format(DISPLAY_UNIT, displayUnit, SECONDS, seconds))
        pref.edit()
            .putString(DISPLAY_UNIT, displayUnit.name)
            .putString(SECONDS, seconds.toString())
            .apply()
    }

    private fun getString(key: String) : String? {
        return try {
            pref.getString(key, null)
        } catch (e: ClassCastException) {
            Log.w(LOG_KEY, "Invalid %s string: %s".format(key, e))
            null
        }
    }

}