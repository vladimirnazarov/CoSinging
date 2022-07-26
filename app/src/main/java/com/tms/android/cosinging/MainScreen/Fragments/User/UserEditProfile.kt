package com.tms.android.cosinging.MainScreen.Fragments.User

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.canhub.cropper.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.R
import com.tms.android.cosinging.MainScreen.MainActivity

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

    private var photoUrl = ""

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

        val fireAuth = (activity as MainActivity?)!!.getUserFireAuth()
        val fireStore = (activity as MainActivity?)!!.getUserFirestore()
        val storage = (activity as MainActivity?)!!.getUserStorage()
        val users = (activity as MainActivity?)!!.getUsers()

        val cropImage = cropImageVar(fireAuth, storage)

        buttonBack.setOnClickListener {
            (activity as MainActivity?)!!.onBackPressed()
        }

        editImage.setOnClickListener {
            cropImage.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                    setAspectRatio(1, 1)
                    setRequestedSize(600,600)
                }
            )
        }

        buttonConfirm.setOnClickListener {
            val editedHashMap = editedHashMapReturn()

            val userEdited = userEditedReturn(editedHashMap)

            fireStore.collection("MusicianAccount").document("user${userEdited.id}").set(editedHashMap)

            userHashMap["id"]?.let { it1 -> users.child(it1) }?.setValue(userEdited)?.addOnSuccessListener {
                (activity as MainActivity?)!!.setUserHash(editedHashMap)
                Toast.makeText(context, "Changes saved!", Toast.LENGTH_SHORT).show()
                (activity as MainActivity?)!!.onBackPressed()
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

    private fun editedHashMapReturn(): HashMap<String, String> {
        checkPhotoUrl()

        return hashMapOf(
            "name" to editName.text.toString(),
            "nickname" to editNickname.text.toString(),
            "profession" to editProfession.text.toString(),
            "phone" to editPhoneNumber.text.toString(),
            "aboutMe" to editUserInformation.text.toString(),
            "email" to userHashMap["email"] as String,
            "photoLink" to photoUrl,
            "id" to userHashMap["id"] as String,
            "password" to userHashMap["password"] as String
        )
    }

    private fun userEditedReturn(editedHashMap: HashMap<String, String>): Musician{

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

        return userEdited
    }

    private fun cropImageVar(fireAuth: FirebaseAuth, storage: StorageReference): ActivityResultLauncher<CropImageContractOptions> {
        val cropImage = registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                val uriContent = result.uriContent
                if (uriContent != null) {
                    fireAuth.currentUser?.let { storage.child("Profile Images").child(it.uid) }?.putFile(uriContent)
                        ?.addOnCompleteListener {
                            storage.child("Profile Images").child(fireAuth.currentUser!!.uid).downloadUrl.addOnCompleteListener {
                                if (it.isSuccessful){
                                    photoUrl = it.result.toString()
                                    editImage.load(photoUrl){
                                        crossfade(true)
                                        transformations(RoundedCornersTransformation(32f))
                                    }
                                }
                            }
                            Toast.makeText(context, "Image was successfully loaded!", Toast.LENGTH_SHORT).show()
                        }?.addOnFailureListener {
                            Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                val exception = result.error
            }
        }

        return cropImage
    }

    private fun checkPhotoUrl(){
        if (photoUrl == ""){
            photoUrl = userHashMap["photoLink"] as String
        }
    }
}