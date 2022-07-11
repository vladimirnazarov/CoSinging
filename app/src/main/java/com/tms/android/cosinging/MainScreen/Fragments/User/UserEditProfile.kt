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
import coil.load
import coil.transform.RoundedCornersTransformation
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

        editImage.load("https://www.buro247.ua/images/2017/09/neytiri-avatar-5824.jpg"){
            crossfade(true)
            transformations(RoundedCornersTransformation(32f))
        }

        buttonBack.setOnClickListener {
            (activity as MainActivity?)!!.onBackPressed()
        }

        buttonConfirm.setOnClickListener {
            Toast.makeText(context, "Confirmed!", Toast.LENGTH_SHORT).show()
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
}