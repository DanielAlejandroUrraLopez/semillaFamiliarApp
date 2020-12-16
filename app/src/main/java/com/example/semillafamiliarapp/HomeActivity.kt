package com.example.semillafamiliarapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.semillafamiliarapp.utils.CircleTrasForm
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType {
    BASIC,
    GOOGLE,
    FACEBOOK
}

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
         /*esta es una prueba*/
        setTheme(R.style.OverlayThemeLime)
         /*fin esta es una prueba*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // setup
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")
        val nomUser: String? = bundle?.getString("nomUser")
        setup(email, provider,nomUser)

        // Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.putString("nomUser", nomUser)
        prefs.apply()
    }

    private fun setup(email: String?, provider: String?,nomUser: String?) {

        title = "Inicio"

        if(email.isNullOrEmpty()){
            emailTextView.text = nomUser
        }else{
            emailTextView.text = email
        }

        providerTextView.text = provider

        Picasso.with(this)
           /* .load("@drawable/imgavatar")*/
            .load(R.drawable.imgavatar)
            .fit()
            .transform(CircleTrasForm()) //renderizar como un circulo
            .into(imageViewAvatar)

        //borrar info de login
        logOutButton.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            if (provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

    }
}