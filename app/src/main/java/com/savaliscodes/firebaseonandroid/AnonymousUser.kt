package com.savaliscodes.firebaseonandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class AnonymousUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anonymous_user)

        val tvMsg = "You Are Logged In Anonymously"
        val textView = findViewById<TextView>(R.id.tvMes)
            textView.text = tvMsg
        val userMsg:String = intent.getStringExtra("signIn_message").toString()

        Toast.makeText(this, userMsg, Toast.LENGTH_SHORT).show()
    }
}