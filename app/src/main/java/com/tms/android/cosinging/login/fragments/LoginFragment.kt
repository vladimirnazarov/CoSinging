package com.tms.android.cosinging.login.fragments

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.tms.android.cosinging.R
import com.tms.android.cosinging.login.LogOrRegActivity

class LoginFragment: Fragment() {

    private lateinit var enterEmail: EditText
    private lateinit var enterPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var forgetPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_frame, container, false)

        findElements(view)

        loginButton.setOnClickListener{

            (activity as LogOrRegActivity?)!!.nextActivity()
            Toast.makeText(context, "Successfully logged in!", Toast.LENGTH_SHORT).show()
        }

        forgetPassword.isClickable = true
        forgetPassword.setTextColor(Color.parseColor("#344feb"))

        forgetPassword.setOnClickListener {
            Toast.makeText(context, "Will be soon!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        (activity as LogOrRegActivity?)!!.showButton()
    }

    companion object{
        fun newInstance(): LoginFragment = LoginFragment()
    }

    private fun findElements(view: View){
        enterEmail = view.findViewById(R.id.login_enter_email) as EditText
        enterPassword = view.findViewById(R.id.login_enter_password) as EditText
        loginButton = view.findViewById(R.id.login_button) as Button
        forgetPassword = view.findViewById(R.id.login_forget_password) as TextView
    }
}