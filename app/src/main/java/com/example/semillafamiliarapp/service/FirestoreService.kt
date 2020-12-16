package com.example.semillafamiliarapp.service

import com.example.semillafamiliarapp.enum.UtilLongEnum
import com.example.semillafamiliarapp.enum.UtilStringEnum
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {

    val db = FirebaseFirestore.getInstance()

    fun validateCreateUser(nomUser: String?): Int{
        val capitalCities = db.collection(UtilStringEnum.USERS.text)
            .whereEqualTo(UtilStringEnum.NOMUSER.text, nomUser).get()
        Thread.sleep(UtilLongEnum.DOS_MIL.numLong)

        return capitalCities?.result?.documents?.size!!
    }

    fun createUser(nomUser: String,password: String){
        db.collection(UtilStringEnum.USERS.text).document(nomUser).set(
            hashMapOf(UtilStringEnum.NOMUSER.text to nomUser,UtilStringEnum.PASS.text to password)
        )
    }

    fun validatePassUserNameUser(nomUser: String?,password: String?): Int{
        val capitalCities = db.collection(UtilStringEnum.USERS.text)
            .whereEqualTo(UtilStringEnum.NOMUSER.text, nomUser)
            .whereEqualTo(UtilStringEnum.PASS.text, password).get()
        Thread.sleep(UtilLongEnum.DOS_MIL.numLong)

        return capitalCities?.result?.documents?.size!!
    }
}