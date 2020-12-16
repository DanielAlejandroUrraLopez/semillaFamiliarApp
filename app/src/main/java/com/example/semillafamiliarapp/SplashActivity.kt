package com.example.semillafamiliarapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import java.util.*
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Glide.with(this).load(R.drawable.mysplash).into(imageViewSplash)

        Timer().schedule(object:  TimerTask(){

            override fun run() {
                val intent = Intent( this@SplashActivity,StartActivity::class.java )
                startActivity(intent)
                finish()
            }

        }, 3000L)


    }
}