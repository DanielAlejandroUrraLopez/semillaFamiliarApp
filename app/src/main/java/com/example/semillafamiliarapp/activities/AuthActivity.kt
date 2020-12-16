package com.example.semillafamiliarapp.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.semillafamiliarapp.R
import com.example.semillafamiliarapp.enum.ProviderTypeEnum
import com.example.semillafamiliarapp.enum.UtilStringEnum
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_home.emailTextView
import kotlinx.android.synthetic.main.activity_home.logOutButton

private val GOOGLE_SIGN_IN = 100

private val callbackManager = CallbackManager.Factory.create()

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(UtilStringEnum.MESSAGE.text, UtilStringEnum.INTEGRACION_DE_FIREBASE_COMPLETA.text)
        analytics.logEvent(UtilStringEnum.INITSCREEM.text, bundle)

        //setup
        setup()
        session()
    }

    override fun onStart() {
        super.onStart()
        authLayout.visibility = View.VISIBLE
    }

    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString(UtilStringEnum.EMAIL.text, null)
        val provider = prefs.getString(UtilStringEnum.PROVIDER.text, null)

        if(email != null && provider != null){
            authLayout.visibility = View.INVISIBLE
            showHome(email, ProviderTypeEnum.valueOf(provider))
        }
    }

    private fun setup(){
        title = UtilStringEnum.AUTENTICACION.text

        logOutButton.setOnClickListener{
            if(emailTextView.text.isNotEmpty() && passwordTextView.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailTextView.text.toString(), passwordTextView.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderTypeEnum.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }

        loginButton.setOnClickListener{
            if(emailTextView.text.isNotEmpty() && passwordTextView.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailTextView.text.toString(), passwordTextView.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderTypeEnum.BASIC)
                        }else{
                            showAlert()
                        }
                    }
            }
        }

        googleButton.setOnClickListener{
            // configuracion
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        facebookButton.setOnClickListener {

            LoginManager.getInstance().logInWithReadPermissions(this, listOf(UtilStringEnum.EMAIL.text))

            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult>{

                    override fun onSuccess(result: LoginResult?) {
                        result?.let {
                            val token = it.accessToken

                            val credential = FacebookAuthProvider.getCredential(token.token)
                            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    showHome(it.result?.user?.email ?: "", ProviderTypeEnum.FACEBOOK)
                                }else{
                                    showAlert()
                                }
                            }
                        }
                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException?) {
                        showAlert()
                    }
                })
        }

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(UtilStringEnum.ERROR.text)
        builder.setMessage(UtilStringEnum.SE_HA_PRODUCIDO_UN_ERROR_AUTENTICANDO_AL_USUARIO.text)
        builder.setPositiveButton(UtilStringEnum.ACEPTAR.text,null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderTypeEnum) {

        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra(UtilStringEnum.EMAIL.text, email)
            putExtra(UtilStringEnum.PROVIDER.text, provider.name)
        }
        startActivity(homeIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{

                val account = task.getResult(ApiException::class.java)

                if(account != null){

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(account.email ?: "", ProviderTypeEnum.GOOGLE)
                        }else{
                            showAlert()
                        }
                    }

                }

            }catch (e: ApiException){

                showAlert()
            }

        }
    }
}