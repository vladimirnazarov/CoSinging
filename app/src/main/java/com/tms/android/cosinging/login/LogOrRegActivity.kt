package com.tms.android.cosinging.login

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.navigation.Navigation
import com.tms.android.cosinging.R
import com.tms.android.cosinging.login.fragments.LoginFragment
import com.tms.android.cosinging.login.fragments.RegisterFragment
import com.tms.android.cosinging.mainScreen.MainActivity

class LogOrRegActivity : AppCompatActivity() {

    private lateinit var registerButton: Button

    override fun onBackPressed() {
        Navigation.findNavController(this, R.id.login_activity_fragments_frame).navigate(R.id.action_registerFragment_to_loginFragment)
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
        startActivity(intent)
    }
}