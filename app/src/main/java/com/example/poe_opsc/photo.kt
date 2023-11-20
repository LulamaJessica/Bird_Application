package com.example.poe_opsc

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract

import com.example.poe_opsc.databinding.ActivityPhotoBinding

class photo : AppCompatActivity() {
    private lateinit var ImageView: ImageView
    private lateinit var upload: Button
    private val REQUEST_IMAGE_CAPTURE = 100
    //view binding
    private lateinit var binding: ActivityPhotoBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImageView = findViewById(R.id.imageView)
        upload = findViewById(R.id.btn_upload)

        //handle click , next page
        binding.Next1.setOnClickListener {
            val intent = Intent(this, Donation::class.java)
            startActivity(intent)
        }
        //This functions is when the user is taking  a photo//
        upload.setOnClickListener {
            val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(takePicIntent)

            try {
                startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE)

            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()

            }

        }
    }

    //This is to display the image back at the app//
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            ImageView.setImageBitmap(imageBitmap)

        } else {

            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
