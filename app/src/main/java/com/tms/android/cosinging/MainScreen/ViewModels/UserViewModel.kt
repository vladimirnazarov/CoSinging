package com.tms.android.cosinging.MainScreen.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.tms.android.cosinging.MainScreen.Data.Musician

class UserViewModel: ViewModel() {

    private var fireDatabase = FirebaseDatabase.getInstance()
    private var fireAuth = FirebaseAuth.getInstance()
    val fireStore = FirebaseFirestore.getInstance()
    var users = fireDatabase.getReference("User")

    var userHash = hashMapOf("not_empty unit" to "not_null")
}