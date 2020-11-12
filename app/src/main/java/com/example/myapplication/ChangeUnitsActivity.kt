package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class ChangeUnitsActivity : AppCompatActivity() {

    var conversionType = arrayOf("Yards", "Meters", "Miles")
    var firstSelected = ""
    var secondSelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_units)
        setSupportActionBar(findViewById(R.id.toolbar))

        //receive mode type from the main screen
        val payload = intent
        if (payload.hasExtra("MODE")){
            val temp = payload.getStringExtra("MODE")
            if (temp == "Length") {
                // do nothing, since the array is already in length measurements
            }
            else if (temp == "Volume"){
                conversionType = arrayOf("Gallons", "Liters", "Quarts")
            }
        }

        //our 2 spinners
        val firstSpinner = findViewById<Spinner>(R.id.spinner1)
        val secondSpinner = findViewById<Spinner>(R.id.spinner2)

        //create array adapter for each spinner
        val arrayAdatper1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, conversionType)
        val arrayAdatper2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, conversionType)
        firstSpinner.adapter = arrayAdatper1
        secondSpinner.adapter = arrayAdatper2

        //give spinner 1 some action
        firstSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                firstSelected = conversionType[position]
            }

        }

        //give spinner 2 some action
        secondSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                secondSelected = conversionType[position]
            }

        }




        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val mainItent = Intent(this, CalculateActivity::class.java)
            mainItent.putExtra("FROM", firstSelected)
            mainItent.putExtra("TO", secondSelected)
            startActivity(mainItent)
            finish()
        }
    }
}