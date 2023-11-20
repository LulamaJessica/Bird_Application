package com.example.poe_opsc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Setting : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var textView: TextView
    private lateinit var switchUnit: Switch
    private lateinit var nextButton: Button
    private lateinit var saveButton: Button
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        seekBar = findViewById(R.id.seekBar)
        textView = findViewById(R.id.textView)
        switchUnit = findViewById(R.id.switchUnit)
        nextButton = findViewById(R.id.nextButton)
        saveButton = findViewById(R.id.saveButton)

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().reference.child("Settings")

        // Set max value for SeekBar
        seekBar.max = 100

        // Set up SeekBar change listener
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the TextView with the current SeekBar progress
                textView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not needed for this example
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not needed for this example
            }
        })

        nextButton.setOnClickListener {


            val intent = Intent(this, Observation::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            // Add code for handling the "Save to Firebase" click
            val progress = seekBar.progress
            val isUsingKm = switchUnit.isChecked
            saveToFirebase(progress, isUsingKm)

        }

        switchUnit.setOnCheckedChangeListener { _, isChecked ->
            // Handle the switch state change
            if (isChecked) {
                switchUnit.text = "Miles"
            } else {
                switchUnit.text = "Km"
            }
        }
    }

    private fun saveToFirebase(progress: Int, isUsingKm: Boolean) {
        // Save the progress value and selected unit to Firebase Realtime Database
        val data = hashMapOf(
            "progress" to progress,
            "isUsingKm" to isUsingKm
        )
        databaseReference.setValue(data)
    }
}