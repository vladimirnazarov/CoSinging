package com.tms.android.cosinging.MainScreen.Fragments.User

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.load
import coil.transform.RoundedCornersTransformation
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.R
import com.tms.android.cosinging.MainScreen.MainActivity
import com.tms.android.cosinging.MainScreen.ViewModels.UserViewModel

class UserEditProfile: Fragment() {

    private lateinit var editImage: ImageView
    private lateinit var editName: EditText
    private lateinit var editProfession: EditText
    private lateinit var editNickname: EditText
    private lateinit var editPhoneNumber: EditText
    private lateinit var editUserInformation: EditText

    private lateinit var buttonBack: Button
    private lateinit var buttonConfirm: Button

    private lateinit var userHashMap: HashMap<String, String>

    private val userViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_edit_profile, container, false)

        findElements(view)

        loadData()

        buttonBack.setOnClickListener {
            (activity as MainActivity?)!!.onBackPressed()
        }

        buttonConfirm.setOnClickListener {
            val editedHashMap = hashMapOf(
                "name" to editName.text.toString(),
                "nickname" to editNickname.text.toString(),
                "profession" to editProfession.text.toString(),
                "phone" to editPhoneNumber.text.toString(),
                "aboutMe" to editUserInformation.text.toString(),
                "email" to userHashMap["email"] as String,
                "photoLink" to userHashMap["photoLink"] as String,
                "id" to userHashMap["id"] as String,
                "password" to userHashMap["password"] as String
            )

            val userEdited = Musician()
            userEdited.id = editedHashMap["id"].toString()
            userEdited.aboutMe = editedHashMap["aboutMe"].toString()
            userEdited.name = editedHashMap["name"].toString()
            userEdited.email = editedHashMap["email"].toString()
            userEdited.nickname = editedHashMap["nickname"].toString()
            userEdited.password = editedHashMap["password"].toString()
            userEdited.phone = editedHashMap["phone"].toString()
            userEdited.photoLink = editedHashMap["photoLink"].toString()
            userEdited.profession = editedHashMap["profession"].toString()

            userViewModel.fireStore.collection("MusicianAccount").document("user${userEdited.id}").set(editedHashMap)

            userHashMap["id"]?.let { it1 -> userViewModel.users.child(it1) }?.setValue(userEdited)?.addOnSuccessListener {
                (activity as MainActivity?)!!.userHashMap = editedHashMap
                Toast.makeText(context, "Changes saved!", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    companion object{
        fun newInstance(): UserEditProfile = UserEditProfile()
    }

    private fun findElements(view: View){
        editImage = view.findViewById(R.id.edit_profile_avatar) as ImageView
        editName = view.findViewById(R.id.edit_profile_name) as EditText
        editProfession = view.findViewById(R.id.edit_profile_profession) as EditText
        editNickname = view.findViewById(R.id.edit_profile_nickname) as EditText
        editPhoneNumber = view.findViewById(R.id.edit_profile_phone_number) as EditText
        editUserInformation = view.findViewById(R.id.edit_profile_about_me) as EditText
        buttonBack = view.findViewById(R.id.edit_profile_button_back) as Button
        buttonConfirm = view.findViewById(R.id.edit_profile_button_confirm) as Button
    }

    private fun loadData(){
        arguments?.let { bundle ->
            userHashMap = bundle.getSerializable("userHashMap") as HashMap<String, String>
        }
        editName.setText(userHashMap["name"])
        editProfession.setText(userHashMap["profession"])
        editNickname.setText(userHashMap["nickname"])
        editPhoneNumber.setText(userHashMap["phone"])
        editUserInformation.setText(userHashMap["aboutMe"])

        editImage.load(userHashMap["photoLink"]){
            crossfade(true)
            transformations(RoundedCornersTransformation(32f))
        }
    }
}