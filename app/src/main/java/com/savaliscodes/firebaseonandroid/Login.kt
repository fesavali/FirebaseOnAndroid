package com.savaliscodes.firebaseonandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tvSignIn = findViewById<TextView>(R.id.tvMessage)
        val countries = ArrayList<String>()
        countries.add("KE")
        countries.add("US")
        countries.add("TZA")
        countries.add("UGA")
        val auth = FirebaseAuth.getInstance()
        val btnAnon = findViewById<Button>(R.id.Anon_sign)

        //other sign in options
        val btnSignIn = findViewById<Button>(R.id.signIn)
        btnSignIn.setOnClickListener {
            val providers = arrayListOf(
               AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().setWhitelistedCountries(countries).setDefaultCountryIso("KE").build()
            )
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(!BuildConfig.DEBUG, true)
                .setTheme(R.style.SigninTheme)
                .build(), RC_SIGN_IN)
        }

        if(intent.hasExtra(SIGN_MESSAGE)){
            btnAnon.visibility = INVISIBLE
            tvSignIn.text = intent.getStringExtra(SIGN_MESSAGE)
            }else {
            //do anonymous sign in
            btnAnon.setOnClickListener {
                auth.signInAnonymously()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            loadListActivity()
                        } else {
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun loadListActivity() {
        val user = FirebaseAuth.getInstance().currentUser
        val intent = Intent(this, AnonymousUser::class.java)
        intent.putExtra(SIGN_MESSAGE, "signIn to view this page")
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(USER_ID, user!!.uid)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Login operation failed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    companion object {
        const val USER_ID = "user_id"
        const val RC_SIGN_IN = 15
        const val SIGN_MESSAGE = "signIn_message"
    }
}