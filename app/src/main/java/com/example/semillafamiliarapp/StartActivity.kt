package com.example.semillafamiliarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)



        //setup
        setup()
    }

    private fun setup (){
        padreButton.setOnClickListener{
            showLogin()
        }

        hijoButton.setOnClickListener{
            showLoginHijo()
        }
    }

    private fun showLogin() {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        startActivity(new Intent(this,AuthActivity.class))
        finish()
        /*val loginIntent = Intent(this, AuthActivity::class.java)*/
        /*startActivity(loginIntent)*/
    }

    private fun showLoginHijo() {

        val loginIntent = Intent(this, AuthHijoActivity::class.java)
        startActivity(loginIntent)
    }
}