package com.tms.android.cosinging.Login.Fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tms.android.cosinging.R
import com.tms.android.cosinging.Login.LogOrRegActivity
import com.tms.android.cosinging.Utils.AppValueEventListener

class LoginFragment: Fragment() {

    private lateinit var enterEmail: EditText
    private lateinit var enterPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var forgetPassword: TextView

    private lateinit var adminButton: Button

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

        var fireDatabase = (activity as LogOrRegActivity?)!!.fireDatabase
        val fireAuth = (activity as LogOrRegActivity?)!!.fireAuth
        val fireStore = (activity as LogOrRegActivity?)!!.fireStore
        val users = (activity as LogOrRegActivity?)!!.users
        val regex = Regex("[a-zA-Z0-9.]*@[a-zA-Z]*\\.[a-zA-Z]*")

        val musicianAccountBase = fireStore.collection("MusicianAccount")
        var emailSimilar = ""
        var correctPassword = ""

        adminButton.setOnClickListener {
            fireAuth.signInWithEmailAndPassword("admin@gmail.com", "123456").addOnSuccessListener {
                Toast.makeText(context, "Welcome back, my Master!", Toast.LENGTH_SHORT).show()
                (activity as LogOrRegActivity?)!!.users.child(fireAuth.uid.toString()).get().addOnSuccessListener{
                    (activity as LogOrRegActivity?)!!.userHashMap = it.value as HashMap<String, String>
                    (activity as LogOrRegActivity?)!!.nextActivity()
                }
            }
        }

        loginButton.setOnClickListener {
            musicianAccountBase.whereEqualTo("email", enterEmail.text.toString()).get().addOnSuccessListener {
                it.forEach {
                    emailSimilar = it.get("email") as String
                    correctPassword = it.get("password") as String
                }
                if (TextUtils.isEmpty(enterEmail.text.toString())){
                    Toast.makeText(context, "Enter your email", Toast.LENGTH_SHORT).show()
                } else if (!enterEmail.text.matches(regex)){
                    Toast.makeText(context, "Invalid mail format", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(enterPassword.text.toString())){
                    Toast.makeText(context, "Enter your password", Toast.LENGTH_SHORT).show()
                } else if (enterPassword.length() < 6){
                    Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show()
                } else if (emailSimilar == ""){
                    Toast.makeText(context, "User is not found", Toast.LENGTH_SHORT).show()
                } else if (enterPassword.text.toString() != correctPassword){
                    Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show()
                }
                else if (enterEmail.text.toString() == emailSimilar && enterPassword.text.toString() == correctPassword){
                    fireAuth.signInWithEmailAndPassword(enterEmail.text.toString(), enterPassword.text.toString()).addOnSuccessListener {
                        Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        (activity as LogOrRegActivity?)!!.users.child(fireAuth.uid.toString()).get().addOnSuccessListener{
                            (activity as LogOrRegActivity?)!!.userHashMap = it.value as HashMap<String, String>
                            (activity as LogOrRegActivity?)!!.nextActivity()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

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

        adminButton = view.findViewById(R.id.admin_button) as Button
    }
}