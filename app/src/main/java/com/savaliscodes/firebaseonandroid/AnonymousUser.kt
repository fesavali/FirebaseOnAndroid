package com.savaliscodes.firebaseonandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AnonymousUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anonymous_user)
//handle account management
        val authUi = AuthUI.getInstance()
        val del = findViewById<Button>(R.id.btnDelete)
        val out = findViewById<Button>(R.id.btnLogout)

        out.setOnClickListener {
            authUi
                .signOut(this)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        startActivity(Intent(this,Login::class.java))
                    }else{
                        Toast.makeText(this, "Logout Process Failed. Try again", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        del.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Delete This Account")
                .setMessage("This is Permanent. Are you Sure?")
                .setPositiveButton("Yes"){_,_ ->
                    authUi.delete(this)
                        .addOnCompleteListener { task->
                            if (task.isSuccessful){
                                startActivity(Intent(this,Login::class.java))
                            }else{
                                Toast.makeText(this, "Account Delete Failed. Try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                .setNegativeButton("No", null)
                .show()
        }


        val tvMsg = "You Are Logged In Anonymously"
        val textView = findViewById<TextView>(R.id.tvMes)
            textView.text = tvMsg
        val userMsg:String = intent.getStringExtra("signIn_message").toString()

        Toast.makeText(this, userMsg, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        AuthUI.getInstance().signOut(this)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    startActivity(Intent(this,Login::class.java))
                }else{
                    Toast.makeText(this, "Logout Process Failed. Try again", Toast.LENGTH_SHORT).show()
                }
            }
    }
}