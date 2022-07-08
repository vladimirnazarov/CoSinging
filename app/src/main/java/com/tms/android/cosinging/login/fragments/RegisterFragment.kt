package com.tms.android.cosinging.login.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.tms.android.cosinging.R
import com.tms.android.cosinging.login.LogOrRegActivity

class RegisterFragment: Fragment() {

    private lateinit var enterEmail: EditText
    private lateinit var enterPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.register_frame, container, false)

        findElements(view)

        registerButton.setOnClickListener{
            Toast.makeText(context, "Successfully registered!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        (activity as LogOrRegActivity?)!!.hideButton()
    }

    companion object{
        fun newInstance(): RegisterFragment = RegisterFragment()
    }

    private fun findElements(view: View){
        enterEmail = view.findViewById(R.id.register_enter_email) as EditText
        enterPassword = view.findViewById(R.id.register_enter_password) as EditText
        confirmPassword = view.findViewById(R.id.register_confirm_password) as EditText
        registerButton = view.findViewById(R.id.register_button) as Button
    }
}