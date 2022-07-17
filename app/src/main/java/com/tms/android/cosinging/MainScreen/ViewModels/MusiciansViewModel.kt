package com.tms.android.cosinging.MainScreen.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.MainScreen.MainActivity

class MusiciansViewModel: ViewModel() {

    private var fireDatabase = FirebaseDatabase.getInstance()
    private var fireAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private var users = fireDatabase.getReference("User")

    val musicianList = mutableListOf<Musician>()

    private val linkImg = "https://i.pinimg.com/originals/3f/e9/46/3fe9467eccd40719db3707ceafaa2725.jpg"
    private val name = "Егор Плащинский"
    private val profession = "Гитарист"

    init {
//        for(index in 0..100){
//            val musician = Musician(id = index.toString())
//            musician.name = this.name
//            musician.profession = this.profession
//            musician.photoLink = linkImg
//
//            musicianList += musician
//        }
    }
}