package com.opgaver.recordingplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    // Null until onCreateView.
    private var toPlans_Button: Button? = null
    private var toRecordings_Button: Button? = null

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAnalytics = Firebase.analytics
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toPlans_Button = findViewById(R.id.toPlans_Button)
        toPlans_Button!!.setOnClickListener {
            val intent = Intent(this, PlansFrameActivity::class.java)
            startActivity(intent)
        }

        toRecordings_Button = findViewById(R.id.toRecordings_button)
        toRecordings_Button!!.setOnClickListener {
            throw RuntimeException("Crashing this App with no survivors")
        }

    }



}