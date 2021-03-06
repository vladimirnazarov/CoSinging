package com.tms.android.cosinging.MainScreen.Fragments.Musician

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.tms.android.cosinging.MainScreen.MainActivity
import com.tms.android.cosinging.R
import org.w3c.dom.Text
import java.util.HashMap

class MusicianCardFullScreen: Fragment() {

    private lateinit var musicianAvatar: ImageView
    private lateinit var musicianFavourite: ImageView
    private lateinit var musicianName: TextView
    private lateinit var musicianProfession: TextView
    private lateinit var musicianNickname: TextView
    private lateinit var musicianPostbox: TextView
    private lateinit var musicianPhoneNumber: TextView
    private lateinit var musicianInformation: TextView
    private lateinit var musicianChatButton: Button

    private lateinit var musicianHash: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.musician_profile, container, false)

        findElements(view)

        musicianLoadHashAndData()

        musicianChatButton.setOnClickListener {
            Toast.makeText(context, "Will be soon!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    companion object{
        fun newInstance(): MusicianCardFullScreen = MusicianCardFullScreen()
    }

    private fun findElements(view: View){
        musicianAvatar = view.findViewById(R.id.musician_profile_avatar) as ImageView
        musicianFavourite = view.findViewById(R.id.musician_profile_favourite) as ImageView
        musicianName = view.findViewById(R.id.musician_profile_name) as TextView
        musicianNickname = view.findViewById(R.id.musician_profile_nickname) as TextView
        musicianPostbox = view.findViewById(R.id.musician_profile_postbox) as TextView
        musicianPhoneNumber = view.findViewById(R.id.musician_profile_number) as TextView
        musicianProfession = view.findViewById(R.id.musician_profile_profession) as TextView
        musicianInformation = view.findViewById(R.id.musician_profile_information) as TextView
        musicianChatButton = view.findViewById(R.id.musician_profile_chat) as Button
    }

    private fun musicianLoadHashAndData(){
        arguments?.let { bundle ->
            musicianHash = bundle.getSerializable("musicianHashToBundle") as HashMap<String, String>
        }

        musicianName.text = musicianHash["name"]
        musicianNickname.text = musicianHash["nickname"]
        musicianPhoneNumber.text = musicianHash["phone"]
        musicianPostbox.text = musicianHash["email"]
        musicianInformation.text = musicianHash["aboutMe"]
        musicianProfession.text = musicianHash["profession"]

        musicianAvatar.load(musicianHash["photoLink"]){
            crossfade(true)
            transformations(RoundedCornersTransformation(32f))
        }
    }
}