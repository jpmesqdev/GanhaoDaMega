package com.devdroid.ganhaodamega

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val MAX_NUMBERS = 59
    }

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input: EditText = findViewById(R.id.main_input_number)
        val result: TextView = findViewById(R.id.main_txt_result)
        val btn: Button = findViewById(R.id.main_btn_generate)

        prefs = getSharedPreferences("db", MODE_PRIVATE)

        val lastNumbers = prefs.getString("result", null)

        lastNumbers?.let {
            result.text = lastNumbers
        }

        btn.setOnClickListener {
            val text = input.text.toString()
            generateNumber(text, result)
        }
    }

    private fun generateNumber(input: String, result: TextView) {
        if (input.isEmpty()) {
            Toast.makeText(this, R.string.input_error, Toast.LENGTH_LONG).show()
            return
        }

        val numberQuantity = input.toInt()

        if (numberQuantity !in 6..15) {
            Toast.makeText(this, R.string.input_error, Toast.LENGTH_LONG).show()
            return
        }

        val numberList = mutableSetOf<String>()

        while (true) {
            val randomNumber = Random().nextInt(MAX_NUMBERS) + 1
            // add 0 to number if it has one digit eg: 9 -> 09
            val padNumber = randomNumber.toString().padStart(2, '0')

            numberList.add(padNumber)

            if (numberList.size == numberQuantity) {
                break
            }
        }

        result.text = numberList.sorted().joinToString("-")

        prefs.edit().apply {
            putString("result", result.text.toString())
            apply()
        }

    }

}
