package com.huellbabineaux.huellconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

const val HOURS_PER_HUELL = 1.71666667

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heulls: EditText = findViewById(R.id.heulls_text)
        val units: EditText = findViewById(R.id.units_text)

        heulls.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == heulls) {
                    val text = e.toString()
                    if (text == "") {
                        units.setText("")
                    } else {
                        val value = text.toDouble() * HOURS_PER_HUELL
                        units.setText(String.format("%.3f", value))
                    }
                }
            }
        })

        units.addTextChangedListener(object : Watcher() {
            override fun afterTextChanged(e: Editable?) {
                if (currentFocus == units) {
                    val text = e.toString()
                    if (text == "") {
                        heulls.setText("")
                    } else {
                        val value = text.toDouble() / HOURS_PER_HUELL
                        heulls.setText(String.format("%.3f", value))
                    }
                }
            }
        })

    }

}

abstract class Watcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}