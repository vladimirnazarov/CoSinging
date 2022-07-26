package com.tms.android.cosinging.MainScreen.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

    val hashOfAllUsers: MutableLiveData<HashMap<String, HashMap<String, String>>> = MutableLiveData()

    fun getHashOfAllUsers() = hashOfAllUsers.value

    fun setHashOfAllUsers(hashMap: HashMap<String, HashMap<String, String>>){
        hashOfAllUsers.value = hashMap
    }

    fun getMusicianUsers() = users

    fun getMusicianFireStore() = fireStore

    fun getMusicianFireAuth() = fireAuth
}