package com.savaliscodes.firebaseonandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.savaliscodes.firebaseonandroid.Login.Companion.SIGN_MESSAGE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //check if user is logged in
        val user = FirebaseAuth.getInstance().currentUser
        if(user == null || user.isAnonymous){
            val intent =  Intent(this, AnonymousUser::class.java)
            intent.putExtra(SIGN_MESSAGE, "signIn to view this page")
            startActivity(intent)
        }


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
companion object{
    const val ATTEMPT_SIGN = 10
}

}