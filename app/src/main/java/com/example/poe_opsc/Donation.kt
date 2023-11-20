package com.example.poe_opsc

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.*
import com.example.poe_opsc.databinding.ActivityDonationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Donation : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityDonationBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    companion object {
        private const val NEW_ITEM_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init //firebase  auth
        firebaseAuth = FirebaseAuth.getInstance()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait.....")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click ,go back
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
        //handle click ,begin upload category
        binding.submitBtn.setOnClickListener{
            validateData()
            startActivityForResult(intent,NEW_ITEM_REQUEST_CODE )
        }
        //handle click , next page
        binding.NextBtn.setOnClickListener{
            val intent = Intent(this, Hotspot::class.java)
            startActivity(intent)
        }
    }
    private var donations=""

    private fun validateData(){
        //get data
        donations=binding.categoryEt.text.toString().trim()

        //validate data
        if(donations.isEmpty()){
            Toast.makeText(this,"Enter Donation Amount...",Toast.LENGTH_SHORT).show()
        }else {
            addCategoryFirebase()
        }
    }
    private fun addCategoryFirebase() {
        //show progress
        progressDialog.show()
        //get timestamp
        val timestamp=System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap=HashMap<String,Any>()
        hashMap["id"] ="$timestamp"
        hashMap["donations"] =donations
        hashMap["timestamp"]=timestamp
        hashMap["uid"] ="${firebaseAuth.uid}"

        //add to firebase db:Database3 Root >Categories >categoryId>category info
        val ref =FirebaseDatabase.getInstance().getReference("Donations")

        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Added successfully",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{ e->
                //failed to add
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()
            }



    }


}
