package com.example.tt_exchangerate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_conversion.*

class Conversion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        button_Conversion.setBackgroundResource(R.drawable.conversion_in)
        button_Home.setBackgroundResource(R.drawable.home)



        val info = intent.extras
        val abbreviation :String = info!!.get("abbreviation") as String

        val a = abbreviation.substringAfter("abbreviationName=")
        val abbreviationBuffer = a.substring(0..2)

        val b = abbreviation.substringAfter("course_nowNumber=")
        val countBuffer = b.substring(0..4)

        abbreviationTV.text = abbreviationBuffer
        out_count.text = "Текущий курс :$countBuffer"

        button_convert.setOnClickListener {
            val buffer_in = in_count.text.toString()
            val a = buffer_in.toDouble()
            val c = countBuffer.toDouble()
            val result = c*a
            out_count.text = result.toString()
        }

        button_Home.setOnClickListener {
            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)
        }
    }
}