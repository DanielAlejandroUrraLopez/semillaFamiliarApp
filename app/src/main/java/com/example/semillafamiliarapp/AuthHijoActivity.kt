package com.example.semillafamiliarapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.semillafamiliarapp.service.FirestoreService
import kotlinx.android.synthetic.main.activity_auth_hijo.*


class AuthHijoActivity : AppCompatActivity() {

    private val firestoreConect = FirestoreService()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.OverlayThemeLime)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth_hijo)


        //setup
        setup()

}


    private  fun setup(){

        registerButton.setOnClickListener {

            if( firestoreConect.validateCreateUser(nomUserTextView.text.toString()) == 0){
                addUserHijo()
                redirectHome()
            }else{
                Toast.makeText(this,"Usuario con ese nombre ya esta registrado!", Toast.LENGTH_SHORT).show()
            }

        }

        loginButton.setOnClickListener {

            if( firestoreConect.validatePassUserNameUser(nomUserTextView.text.toString(),passwordTextView.text.toString()) > 0){
                redirectHome()
            }else{
                Toast.makeText(this,"Usuario o contraseña no coinciden!", Toast.LENGTH_SHORT).show()
            }
        }




    }




    private fun redirectHome(){
        val homeIntent = Intent(this, HomeActivity::class.java).apply{
            putExtra("nomUser",nomUserTextView.text.toString())
        }
        startActivity(homeIntent)
    }

    private fun addUserHijo(){

        firestoreConect.createUser(nomUserTextView.text.toString(),passwordTextView.text.toString())

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("nomUser",nomUserTextView.text.toString())
        prefs.putString("pass",passwordTextView.text.toString())
        prefs.apply()

        Toast.makeText(this,"Usuario creado exitosamente!", Toast.LENGTH_SHORT).show()
    }




}


