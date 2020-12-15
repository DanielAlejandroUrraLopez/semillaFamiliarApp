package com.example.semillafamiliarapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
      title = "Inicio"
        //setup
        setup()

    }

    private fun setup(){
        padreButton.setOnClickListener{
            showLogin()
        }

        hijoButton.setOnClickListener {
            showLoginHijo()
        }
    }

    private fun showLogin() {
        title = "Inicio Padre"
        val loginIntent = Intent(this, AuthActivity::class.java)
        startActivity(loginIntent)
    }

    private fun showLoginHijo() {
        title = "Inicio Hijo"
        val loginIntent = Intent(this, AuthHijoActivity::class.java)
        startActivity(loginIntent)
    }


}