package com.tms.android.cosinging.Login.Fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tms.android.cosinging.R
import com.tms.android.cosinging.Login.LogOrRegActivity
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.Utils.AppValueEventListener

class RegisterFragment: Fragment() {

    private lateinit var enterEmail: EditText
    private lateinit var enterPassword: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button

    private val undefinedPhotoLink = "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg"

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

        registerButton.setOnClickListener{
            musicianAccountBase.whereEqualTo("email", enterEmail.text.toString()).get().addOnSuccessListener { it ->
                it.forEach{
                emailSimilar = it.get("email") as String
            }
                if (TextUtils.isEmpty(enterEmail.text.toString())){
                    Toast.makeText(context, "Enter your email", Toast.LENGTH_SHORT).show()
                } else if (!enterEmail.text.matches(regex)){
                    Toast.makeText(context, "Invalid mail format", Toast.LENGTH_SHORT).show()
                } else if (enterEmail.text.toString() == emailSimilar){
                    Toast.makeText(context, "User with this email is already exists", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(enterPassword.text.toString())){
                    Toast.makeText(context, "Enter your password", Toast.LENGTH_SHORT).show()
                } else if (enterPassword.length() < 6){
                    Toast.makeText(context, "Password length must be between 6 and 32 characters", Toast.LENGTH_SHORT).show()
                } else if (confirmPassword.text.toString() != enterPassword.text.toString()){
                    Toast.makeText(context, "Password mismatch", Toast.LENGTH_SHORT).show()
                } else {
                    fireAuth.createUserWithEmailAndPassword(enterEmail.text.toString(), enterPassword.text.toString()).addOnCompleteListener {
                        val musician = Musician()
                        musician.email = enterEmail.text.toString()
                        musician.password = enterPassword.text.toString()
                        musician.id = fireAuth.uid.toString()
                        musician.name = "user${musician.id}"
                        musician.nickname = "user${musician.id}"
                        musician.phone = "No data"
                        musician.aboutMe = "No data"
                        musician.profession = "No data"
                        musician.photoLink = undefinedPhotoLink

                        val musicianHash = mapOf(
                            "aboutMe" to musician.aboutMe,
                            "email" to musician.email,
                            "password" to musician.password,
                            "id" to musician.id,
                            "name" to musician.name,
                            "nickname" to musician.nickname,
                            "phone" to musician.phone,
                            "photoLink" to musician.photoLink,
                            "profession" to musician.profession
                        )

                        fireStore.collection("MusicianAccount").document(musician.nickname).set(musicianHash)

                        fireAuth.currentUser?.let { it1 -> users.child(it1.uid) }?.setValue(musician)?.addOnSuccessListener {
                                Toast.makeText(context,"Successfully registered!", Toast.LENGTH_SHORT).show()
                                (activity as LogOrRegActivity?)!!.users.child(fireAuth.uid.toString()).get().addOnSuccessListener {
                                        (activity as LogOrRegActivity?)!!.userHashMap = it.value as HashMap<String, String>
                                        (activity as LogOrRegActivity?)!!.nextActivity()
                                    }.addOnFailureListener {
                                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                    }
                            }
                    }
                }
            }
        }

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
