package com.savaliscodes.firebaseonandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        //check if user is logged in
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if(auth.currentUser == null || auth.currentUser!!.isAnonymous){
            val intent =  Intent(this, Login::class.java)
            intent.putExtra(SIGN_MESSAGE, auth.currentUser!!.uid)
            startActivity(intent)
        }


        //implement firebase auth
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


    companion object{
    const val SIGN_MESSAGE = "signIn_message"
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