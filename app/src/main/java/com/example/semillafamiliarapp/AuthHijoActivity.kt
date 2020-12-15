package com.example.semillafamiliarapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_auth_hijo.*
import android.util.Log
import kotlinx.android.synthetic.main.activity_start.*


class AuthHijoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.OverlayThemeLime)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth_hijo)


        //setup
        setup()



    }
    private  fun setup(){

        registerButton.setOnClickListener {

            if(!searchByUsername()) {
                addUserHijo()
                redirectHome()
            }else{
                Toast.makeText(this,"Usuario con ese nombre ya esta registrado!", Toast.LENGTH_SHORT).show()
            }
        }



    }




    private fun redirectHome(){
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
    }

    private fun addUserHijo(){
        db.collection("users").document(nomUserTextView.text.toString()).set(
                hashMapOf("nomUser" to nomUserTextView.text.toString(),"pass" to passwordTextView.text.toString())
        )

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("nomUser",nomUserTextView.text.toString())
        prefs.putString("pass",passwordTextView.text.toString())
        prefs.apply()

        Toast.makeText(this,"Usuario creado exitosamente!", Toast.LENGTH_SHORT).show()
    }

    private fun searchByUsername(): Boolean {

        var valida: Boolean = false

        val capitalCities = db.collection("users")
            .whereEqualTo("nomUser", nomUserTextView.text.toString()).get()

       if(capitalCities?.result?.documents?.size!! > 0){
           valida = true
       }




        return valida

    }


}


