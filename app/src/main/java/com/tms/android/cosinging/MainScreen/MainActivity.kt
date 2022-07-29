package com.tms.android.cosinging.MainScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.*
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.MainScreen.ViewModels.MusiciansViewModel
import com.tms.android.cosinging.MainScreen.ViewModels.UserViewModel
import com.tms.android.cosinging.R
import com.tms.android.cosinging.Utils.AppValueEventListener

private const val EXTRA_USER_DATA = "CURRENT USER DATA"

class MainActivity : AppCompatActivity() {

    var userHashMap: HashMap<String, String> = HashMap()
    var musiciansHashMap: HashMap<String, HashMap<String, String>> = HashMap()

    val musicianLiveData: MutableLiveData<HashMap<String, HashMap<String, String>>> = MutableLiveData()
    val userLiveData: MutableLiveData<HashMap<String, String>> = MutableLiveData()

    private val userViewModel: UserViewModel by lazy{
        ViewModelProviders.of(this)[UserViewModel::class.java]
    }
    private val musiciansViewModel: MusiciansViewModel by lazy{
        ViewModelProviders.of(this)[MusiciansViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel.userHash.observe(this, Observer{
            userHashMap = it
        })
        musiciansViewModel.hashOfAllUsers.observe(this, Observer{
            musiciansHashMap = it
        })

        readMusiciansData()
        readUserData()
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

    private fun readUserData(){
        if (userViewModel.userHash.value == null) {
            userHashMap = intent.getSerializableExtra("CURRENT USER DATA") as HashMap<String, String>
            userViewModel.userHash.value = userHashMap
        }
        val fireAuthRef = getUserFireAuth()
        val user = fireAuthRef.currentUser?.let { userViewModel.getUsers().child(it.uid) }
        if (user != null) {
            user.addValueEventListener(AppValueEventListener{
                userHashMap = it.value as HashMap<String, String> /* = java.util.HashMap<kotlin.String, kotlin.String> */
                userViewModel.setUserHash(userHashMap)
                userLiveData.value = it.value as HashMap<String, String> /* = java.util.HashMap<kotlin.String, kotlin.String> */
            })
        }
    }

    private fun readMusiciansData(){
        if (musiciansViewModel.hashOfAllUsers.value == null){
            musiciansHashMap = intent.getSerializableExtra("ALL USERS DATA") as HashMap<String, HashMap<String, String> /* = java.util.HashMap<kotlin.String, kotlin.String> */> /* = java.util.HashMap<kotlin.String, java.util.HashMap<kotlin.String, kotlin.String>> */
            musiciansViewModel.setHashOfAllUsers(musiciansHashMap)
        }
        val users = musiciansViewModel.getMusicianUsers()
        users.addValueEventListener(AppValueEventListener{
            musiciansHashMap = it.value as HashMap<String, HashMap<String, String>>
            musiciansViewModel.setHashOfAllUsers(musiciansHashMap)
            musicianLiveData.value = musiciansHashMap
        })
    }

    fun setUserHash(hashMap: HashMap<String, String>){
        userViewModel.setUserHash(hashMap)
    }

    fun getUsers() = userViewModel.getUsers()

    fun getUserFirestore() = userViewModel.getFirestore()

    fun getUserFireAuth() = userViewModel.getFireAuth()

    fun getUserStorage() = userViewModel.getStorage()

    fun getUserFavourites() = userViewModel.getUserFavourites()
}