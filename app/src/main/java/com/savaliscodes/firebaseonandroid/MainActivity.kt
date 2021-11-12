package com.savaliscodes.firebaseonandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //implement firebase auth
        val auth = FirebaseAuth.getInstance()
        val userID:String = intent.getStringExtra("user_id").toString()
        if(userID != null){
            val tvUser = findViewById<TextView>(R.id.user)
            val user = auth.currentUser?.displayName.toString()
            tvUser.text = user
        }else{
            Toast.makeText(this,"No User Found", Toast.LENGTH_SHORT).show()
        }

        val tvID = findViewById<TextView>(R.id.id)

        tvID.text = userID
    }


}