package com.example.poe_opsc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Hotspot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotspot)
            val linkButton = findViewById<Button>(R.id.linkButton)

            linkButton.setOnClickListener {
                // Define the URL you want to open
                val url = "https://www.google.co.za/maps/search/bird+hotstops/@-26.2757625,28.0864522,11z/data=!3m1!4b1?entry=ttu"

                // Create an Intent to open the URL in a browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                // Start the browser activity
                startActivity(intent)
            }

        }
    }