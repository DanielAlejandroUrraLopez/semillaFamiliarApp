package com.example.semillafamiliarapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.semillafamiliarapp.R
import com.example.semillafamiliarapp.enum.ProviderTypeEnum
import com.example.semillafamiliarapp.enum.UtilStringEnum
import com.example.semillafamiliarapp.utils.CircleTrasForm
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*



class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
         /*esta es una prueba*/
        setTheme(R.style.OverlayThemeLime)
         /*fin esta es una prueba*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // setup
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString(UtilStringEnum.EMAIL.text)
        val provider: String? = bundle?.getString(UtilStringEnum.PROVIDER.text)
        val nomUser: String? = bundle?.getString(UtilStringEnum.NOMUSER.text)
        setup(email, provider,nomUser)

        // Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString(UtilStringEnum.EMAIL.text, email)
        prefs.putString(UtilStringEnum.PROVIDER.text, provider)
        prefs.putString(UtilStringEnum.NOMUSER.text, nomUser)
        prefs.apply()
    }

    private fun setup(email: String?, provider: String?,nomUser: String?) {

        title = UtilStringEnum.INICIO.text

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

            if (provider == ProviderTypeEnum.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        shareButton.setOnClickListener {
            val intent = Intent()
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, emailTextView.text.toString())
            intent.putExtra(Intent.EXTRA_SUBJECT,"prueba de envio de datos")
            intent.action = Intent.ACTION_SEND
            val chooserIntent = Intent.createChooser(intent, "elija una opcion")
            startActivity(chooserIntent)
        }

    }
}