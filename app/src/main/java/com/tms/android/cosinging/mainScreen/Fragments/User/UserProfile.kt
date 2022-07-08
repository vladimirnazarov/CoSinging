package com.tms.android.cosinging.mainScreen.Fragments.User

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.tms.android.cosinging.R

class UserProfile: Fragment() {

    private lateinit var nickName: TextView
    private lateinit var name: TextView
    private lateinit var postBox: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var information: TextView
    private lateinit var editProfile: TextView
    private lateinit var settings: TextView
    private lateinit var deleteProfile: TextView
    private lateinit var exitButton: TextView
    private lateinit var avatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_profile, container, false)

        findElements(view)

        avatar.load("https://www.buro247.ua/images/2017/09/neytiri-avatar-5824.jpg"){
            crossfade(true)
            transformations(RoundedCornersTransformation(32f))
        }

        editProfile.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_userProfile_to_userEditProfile)
        }

        return view
    }

    companion object{
        fun newInstance(): UserProfile = UserProfile()
    }

    private fun findElements(view: View){
        name = view.findViewById(R.id.user_name) as TextView
        nickName = view.findViewById(R.id.user_nickname) as TextView
        postBox = view.findViewById(R.id.user_postbox) as TextView
        phoneNumber = view.findViewById(R.id.user_number) as TextView
        information = view.findViewById(R.id.user_information) as TextView
        editProfile = view.findViewById(R.id.user_edit_profile) as TextView
        settings = view.findViewById(R.id.user_settings) as TextView
        deleteProfile = view.findViewById(R.id.user_delete_profile) as TextView
        exitButton = view.findViewById(R.id.user_account_exit) as TextView
        avatar = view.findViewById(R.id.user_avatar) as ImageView
    }
}