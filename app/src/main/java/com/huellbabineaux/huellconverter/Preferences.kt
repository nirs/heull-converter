package com.huellbabineaux.huellconverter

import android.content.SharedPreferences
import android.util.Log
import java.lang.ClassCastException
import java.lang.IllegalArgumentException

const val DISPLAY_UNIT = "displayUnit"
const val INTERVAL = "interval"

class Preferences(private val pref: SharedPreferences) {

    var displayUnit = Units.HOURS  // Default
        get() {
            return try {
                Units.valueOf(getString(DISPLAY_UNIT, field.name))
            } catch (e: IllegalArgumentException) {
                Log.w(LOG_KEY, "Invalid %s value: %s".format(DISPLAY_UNIT, e))
                field
            }
        }
        set(value) {
            pref.edit().putString(DISPLAY_UNIT, value.name).apply()
        }

    var interval = Interval(1.0, Units.HUELLS)  // Default
        get() {
            return try {
                Interval(getString(INTERVAL, field.toString()))
            } catch (e: NumberFormatException) {
                Log.w(LOG_KEY, "Invalid %s value: %s".format(INTERVAL, e))
                field
            }
        }
        set(value) {
            pref.edit().putString(INTERVAL, value.toString()).apply()
        }

    private fun getString(key: String, defValue: String) : String {
        return try {
            pref.getString(key, defValue).orEmpty()
        } catch (e: ClassCastException) {
            Log.w(LOG_KEY, "Invalid %s string: %s".format(key, e))
            defValue
        }
    }

}