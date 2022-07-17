package com.tms.android.cosinging.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.tms.android.cosinging.R
import com.tms.android.cosinging.Login.Fragments.LoginFragment
import com.tms.android.cosinging.Login.Fragments.RegisterFragment
import com.tms.android.cosinging.MainScreen.MainActivity

class LogOrRegActivity : AppCompatActivity() {

    private lateinit var registerButton: Button

    lateinit var userHashMap: HashMap<String, String>

    var fireDatabase = FirebaseDatabase.getInstance()
    var fireAuth = FirebaseAuth.getInstance()
    val fireStore = FirebaseFirestore.getInstance()
    var users = fireDatabase.getReference("User")

    /**
     * Решить ошибку
     * При возврате назад приложение завершается с ошибкой
     **/
    override fun onBackPressed() {
//        if(){
            Navigation.findNavController(this, R.id.login_activity_fragments_frame).navigate(R.id.action_registerFragment_to_loginFragment)
//        } else {

//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_or_reg)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.login_activity_fragments_frame)
        val navController = Navigation.findNavController(this, R.id.login_activity_fragments_frame)

        val loginFragment = LoginFragment.newInstance()
        val registerFragment = RegisterFragment.newInstance()

        if (currentFragment == null){
            supportFragmentManager.beginTransaction().add(R.id.login_activity_fragments_frame, loginFragment).commit()
        }

        if (currentFragment == supportFragmentManager.findFragmentById(R.id.activity_login_login_fragment)){
            registerButton.visibility = VISIBLE
            registerButton.isClickable = true
        }

        registerButton = findViewById(R.id.activity_register_button)
        registerButton.setOnClickListener {
           navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun showButton(){
        registerButton.visibility = VISIBLE
        registerButton.isClickable = true
    }

    fun hideButton(){
        registerButton.visibility = GONE
        registerButton.isClickable = false
    }

    fun nextActivity(){
        val intent = MainActivity.newIntent(this@LogOrRegActivity)
        intent.putExtra("CURRENT USER DATA", userHashMap)
        startActivity(intent)
    }
}