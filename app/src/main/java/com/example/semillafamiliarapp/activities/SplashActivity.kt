package com.example.semillafamiliarapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.semillafamiliarapp.R
import com.example.semillafamiliarapp.enum.UtilLongEnum
import java.util.*
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(object:  TimerTask(){

            override fun run() {
                val intent = Intent( this@SplashActivity, StartActivity::class.java )
                startActivity(intent)
                finish()
            }

        }, UtilLongEnum.TRES_MIL.numLong)


    }
}