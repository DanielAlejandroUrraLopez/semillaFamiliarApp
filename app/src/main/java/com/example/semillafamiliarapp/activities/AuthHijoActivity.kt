package com.example.semillafamiliarapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.semillafamiliarapp.R
import com.example.semillafamiliarapp.enum.UtilIntEnum
import com.example.semillafamiliarapp.enum.UtilStringEnum
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

            if( firestoreConect.validateCreateUser(nomUserTextView.text.toString()) == UtilIntEnum.CERO.num){
                addUserHijo()
                redirectHome()
            }else{
                Toast.makeText(this,UtilStringEnum.USUARIO_CON_ESE_NOMBRE_YA_ESTA_REGISTRADO.text, Toast.LENGTH_SHORT).show()
            }

        }

        loginButton.setOnClickListener {

            if( firestoreConect.validatePassUserNameUser(nomUserTextView.text.toString(),passwordTextView.text.toString()) > UtilIntEnum.CERO.num){
                redirectHome()
            }else{
                Toast.makeText(this,UtilStringEnum.USUARIO_O_CONTRASENA_NO_COINCIDEN.text, Toast.LENGTH_SHORT).show()
            }
        }




    }




    private fun redirectHome(){
        val homeIntent = Intent(this, HomeActivity::class.java).apply{
            putExtra(UtilStringEnum.NOMUSER.text,nomUserTextView.text.toString())
        }
        startActivity(homeIntent)
    }

    private fun addUserHijo(){

        firestoreConect.createUser(nomUserTextView.text.toString(),passwordTextView.text.toString())

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString(UtilStringEnum.NOMUSER.text,nomUserTextView.text.toString())
        prefs.putString(UtilStringEnum.PASS.text,passwordTextView.text.toString())
        prefs.apply()

        Toast.makeText(this,UtilStringEnum.USUARIO_CREADO_EXITOSAMENTE.text, Toast.LENGTH_SHORT).show()
    }




}


