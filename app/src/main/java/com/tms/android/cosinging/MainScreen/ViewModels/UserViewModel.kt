package com.tms.android.cosinging.MainScreen.ViewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tms.android.cosinging.MainScreen.Data.Musician

class UserViewModel: ViewModel() {

    private var fireDatabase = FirebaseDatabase.getInstance()
    private var fireAuth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance().reference
    private var users = fireDatabase.getReference("User")
    private var userFavourites = fireDatabase.getReference("Favourites")

    val userHash: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    fun getUsers() = users

    fun getUserFavourites() = userFavourites

    fun getFirestore() = fireStore

    fun getFireAuth() = fireAuth

    fun getStorage() = storage

    fun getUserHash() = userHash.value

    fun setUserHash(hashMap: HashMap<String, String>){
        userHash.value = hashMap
    }
}