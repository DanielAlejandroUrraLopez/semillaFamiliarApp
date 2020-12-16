package com.example.semillafamiliarapp.service

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_auth_hijo.*

class FirestoreService {

    val db = FirebaseFirestore.getInstance()

    fun validateCreateUser(nomUser: String?): Int{
        val capitalCities = db.collection("users")
            .whereEqualTo("nomUser", nomUser).get()
        Thread.sleep(2000L)

        return capitalCities?.result?.documents?.size!!
    }

    fun createUser(nomUser: String,password: String){
        db.collection("users").document(nomUser).set(
            hashMapOf("nomUser" to nomUser,"pass" to password)
        )
    }

    fun validatePassUserNameUser(nomUser: String?,password: String?): Int{
        val capitalCities = db.collection("users")
            .whereEqualTo("nomUser", nomUser)
            .whereEqualTo("pass", password).get()
        Thread.sleep(2000L)

        return capitalCities?.result?.documents?.size!!
    }
}