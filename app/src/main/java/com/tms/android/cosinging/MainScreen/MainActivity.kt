package com.tms.android.cosinging.MainScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.google.firebase.database.ValueEventListener
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.MainScreen.ViewModels.MusiciansViewModel
import com.tms.android.cosinging.MainScreen.ViewModels.UserViewModel
import com.tms.android.cosinging.R
import com.tms.android.cosinging.Utils.AppValueEventListener

private const val EXTRA_USER_DATA = "CURRENT USER DATA"

class MainActivity : AppCompatActivity() {

    private lateinit var musicianIDGet: String
    var userHashMap: HashMap<String, String> = HashMap()

    private val userViewModel: UserViewModel by lazy{
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel.userHash.observe(this, Observer{
            userHashMap = it
        })

        readData()
    }

    /**
     * В манифесте стоит noHistory
     * Поэтому Main Activity становится главной
     */

    companion object{
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, MainActivity::class.java)
        }
    }

    private fun readData(){
        if (userViewModel.userHash.value == null) {
            userHashMap = intent.getSerializableExtra("CURRENT USER DATA") as HashMap<String, String>
            userViewModel.userHash.value = userHashMap
        }
    }

    fun setUserHash(hashMap: HashMap<String, String>){
        userViewModel.setUserHash(hashMap)
    }

    fun getUsers() = userViewModel.getUsers()

    fun getFirestore() = userViewModel.getFirestore()

    fun getFireAuth() = userViewModel.getFireAuth()
}