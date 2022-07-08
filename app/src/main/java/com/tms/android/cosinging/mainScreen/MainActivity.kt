package com.tms.android.cosinging.mainScreen

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tms.android.cosinging.R
import com.tms.android.cosinging.login.LogOrRegActivity
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object{
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, MainActivity::class.java)
        }
    }
}