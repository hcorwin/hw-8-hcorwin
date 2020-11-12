package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class CalculateActivity : AppCompatActivity() {

    //variable to keep track of the mode we are in
    var modeType = "Length"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        //our number edit text fields
        val fromNumber = findViewById<EditText>(R.id.fromText)
        val toNumber = findViewById<EditText>(R.id.toText)

        //our main screen labels
        val label_from = findViewById<TextView>(R.id.from_label)
        val label_to = findViewById<TextView>(R.id.to_label)
        val title_text = findViewById<TextView>(R.id.titleText)

        //our main screen buttons
        val calculate = findViewById<Button>(R.id.calculateButton)
        val clear = findViewById<Button>(R.id.clearButton)
        val mode = findViewById<Button>(R.id.modeButton)

        //select the values from settings page
        val payload = intent
        if (payload.hasExtra("FROM")){
            label_from.setText(payload.getStringExtra("FROM"))
        }
        if (payload.hasExtra("TO")){
            label_to.setText(payload.getStringExtra("TO"))
        }


        //clear text fields when one is focused
        fromNumber.setOnFocusChangeListener { v, hasFocus ->
            fromNumber.text.clear()
            toNumber.text.clear()
        }
        toNumber.setOnFocusChangeListener { v, hasFocus ->
            fromNumber.text.clear()
            toNumber.text.clear()
        }

        // make the calculation, checking which field has the text
        calculate.setOnClickListener {
            val fromText = fromNumber.text.toString()
            val toText = toNumber.text.toString()
            if (fromText.trim().length > 0){
                val numToConvert = fromText.toDouble() / convert(label_from.text.toString(), label_to.text.toString())
                toNumber.setText(numToConvert.toString())
            }
            if (toText.trim().length > 0) {
                val numToConvert = toText.toDouble() / convert(label_to.text.toString(), label_from.text.toString())
                fromNumber.setText(numToConvert.toString())
            }
            hideKeyboard()
        }

        //clear the text fields and hide the keyboard
        clear.setOnClickListener {
            fromNumber.text.clear()
            toNumber.text.clear()
            hideKeyboard()
        }
        //transition to settings page


        //change the mode of the calculator based on what mode we are currently in
        mode.setOnClickListener {
            if (modeType == "Length") {
                modeType = "Volume"
                title_text.setText("Volume Conversion Calculator")
                label_from.setText("Gallons")
                label_to.setText("Liters")
                fromNumber.text.clear()
                toNumber.text.clear()
            }
            else if (modeType == "Volume") {
                modeType = "Length"
                title_text.setText("Length Conversion Calculator")
                label_from.setText("Yards")
                label_to.setText("Meters")
                fromNumber.text.clear()
                toNumber.text.clear()
            }
            hideKeyboard()
        }
    }

    //hides keyboard
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.go_to_settings){
            val mainIntent = Intent(this, ChangeUnitsActivity::class.java)
            mainIntent.putExtra("MODE", modeType)
            startActivity(mainIntent)
            return true
        }
        return false
    }

    // returns a double based on the strings passed in
    fun convert(from: String, to: String): Double {
        if (from == "Yards"){
            if (to == "Yards")
                return 1.0
            else if (to == "Meters")
                return 1.09361
            else return 1760.0
        }
        if (from == "Meters"){
            if (to == "Meters")
                return 1.0
            else if (to == "Yards")
                return 0.9144
            else return 1609.34
        }
        if (from == "Miles"){
            if (to == "Miles")
                return 1.0
            else if (to == "Yards")
                return 0.000568182
            else return 0.000621371
        }
        if (from == "Liters"){
            if (to == "Liters")
                return 1.0
            else if (to == "Quarts")
                return 0.946353
            else return 3.78541
        }
        if (from == "Gallons"){
            if (to == "Gallons")
                return 1.0
            else if (to == "Quarts")
                return 0.25
            else return 0.264172
        }
        if (from == "Quarts"){
            if (to == "Quarts")
                return 1.0
            else if (to == "Gallons")
                return 4.0
            else return 1.05669
        }
        return 1.0
    }
}