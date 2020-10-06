package com.opgaver.recordingplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    // Null until onCreateView.
    private var toPlans_Button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toPlans_Button = findViewById(R.id.toPlans_Button)
        toPlans_Button!!.setOnClickListener {
            val intent = Intent(this, PlansFrame::class.java)
            startActivity(intent)
        }
    }



}