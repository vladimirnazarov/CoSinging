package com.tms.android.cosinging.MainScreen.Fragments.Musician

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.tms.android.cosinging.MainScreen.Data.FavouriteUser
import com.tms.android.cosinging.R
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.MainScreen.MainActivity
import com.tms.android.cosinging.Utils.AppValueEventListener
import java.lang.Exception

class ListOfMusicians: Fragment() {

    private lateinit var userHashMap: HashMap<String, String>
    private lateinit var musicianHashMap: HashMap<String, HashMap<String, String>>

    private lateinit var musicianListRecyclerView: RecyclerView
    private var adapter: ListOfMusiciansAdapter? = null

    private lateinit var userAvatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_screen_with_list_of_users, container, false)

        musicianListRecyclerView = view.findViewById(R.id.musician_list_total) as RecyclerView
        musicianListRecyclerView.layoutManager = LinearLayoutManager(context)

        loadUserAvatar(view)

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateMusicianHash()

        updateUserHash()

        updateUI(musicianHashMap)
    }


    private inner class ListOfMusiciansHolder(view: View): RecyclerView.ViewHolder(view){

        private lateinit var musician: Musician
        private val name: TextView = itemView.findViewById(R.id.musician_list_name)
        private val profession: TextView = itemView.findViewById(R.id.musician_list_profession)
        private val avatar: ImageView = itemView.findViewById(R.id.musician_list_avatar)
        private val optionsMenu: ImageView = itemView.findViewById(R.id.musician_list_musician_options_menu)
        private lateinit var imgLink: String

        fun bind(musician: Musician){
            this.musician = musician

            name.text = musician.name
            profession.text = musician.profession
            imgLink = musician.photoLink

            avatar.load(imgLink){
                crossfade(true)
                transformations(RoundedCornersTransformation(32f))
            }

            avatar.setOnClickListener {
                val bundle = collectBundle(musician)
                Navigation.findNavController(requireView()).navigate(R.id.action_listOfMusicians_to_musicianCardFullScreen, bundle)
            }

            optionsMenu.setOnClickListener {

                popupAction(it, musician)
            }
        }
    }

    private inner class ListOfMusiciansAdapter(var list: List<Musician>): RecyclerView.Adapter<ListOfMusiciansHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfMusiciansHolder {

            val view = layoutInflater.inflate(R.layout.musician_list_profile, parent, false)
            return ListOfMusiciansHolder(view)
        }

        override fun onBindViewHolder(holder: ListOfMusiciansHolder, position: Int) {

            val musician = list[position]
            musician.password = ""
            holder.bind(musician)
        }

        override fun getItemCount(): Int = list.size
    }

    companion object{
        fun newInstance(): ListOfMusicians = ListOfMusicians()
    }

    private fun updateUI(musicianHashMap: HashMap<String, HashMap<String, String>>){
        val musicianList = mutableListOf<Musician>()

        if (musicianHashMap.containsKey(userHashMap["id"])){
            musicianHashMap.remove(userHashMap["id"])
        }

        for ((key, value) in musicianHashMap) {
            var secondaryMusicianHash: HashMap<String, String> = HashMap()
            secondaryMusicianHash = value
            val musician = Musician(
                email = secondaryMusicianHash["email"] as String,
                aboutMe = secondaryMusicianHash["aboutMe"] as String,
                phone = secondaryMusicianHash["phone"] as String,
                password = secondaryMusicianHash["password"] as String,
                profession = secondaryMusicianHash["profession"] as String,
                photoLink = secondaryMusicianHash["photoLink"] as String,
                id = secondaryMusicianHash["id"] as String,
                name = secondaryMusicianHash["name"] as String,
                nickname = secondaryMusicianHash["nickname"] as String,

            )
            musicianList += musician
        }

        adapter = ListOfMusiciansAdapter(musicianList)
        musicianListRecyclerView.adapter = adapter
    }

    private fun loadUserAvatar(view: View){
        userAvatar = view.findViewById(R.id.main_screen_user_avatar) as ImageView
        userAvatar.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_listOfMusicians_to_userProfile)
        }
    }

    private fun collectBundle(musician: Musician): Bundle {
        val musicianHashToBundle: HashMap<String, String> = hashMapOf(
            "email" to musician.email,
            "aboutMe" to musician.aboutMe,
            "phone" to musician.phone,
            "photoLink" to musician.photoLink,
            "name" to musician.name,
            "nickname" to musician.nickname,
            "profession" to musician.profession)
        val bundle = bundleOf(
            "musicianHashToBundle" to musicianHashToBundle
        )
        return bundle
    }

    private fun updateUserHash(){
        userHashMap = (activity as MainActivity?)!!.userHashMap
        (activity as MainActivity?)!!.userLiveData.observe(viewLifecycleOwner, Observer{
            userHashMap = it
            userAvatar.load(userHashMap["photoLink"]){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        })
    }

    private fun updateMusicianHash(){
        musicianHashMap = (activity as MainActivity?)!!.musiciansHashMap
        (activity as MainActivity?)!!.musicianLiveData.observe(viewLifecycleOwner, Observer{
            musicianHashMap = it
            updateUI(musicianHashMap)
        })
    }

    private fun popupAction(it: View, favouriteMusician: Musician){
        val popupMenu = PopupMenu(context, it)

        val userFavouritesReference = (activity as MainActivity?)!!.getUserFavourites()
        val fireAuthReference = (activity as MainActivity?)!!.getUserFireAuth()
        val favouriteUser = FavouriteUser()

        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.add_to_favourites -> {

                    favouriteUser.email = favouriteMusician.email

                    fireAuthReference.currentUser?.let { it1 ->
                        userFavouritesReference.child(it1.uid).addListenerForSingleValueEvent(AppValueEventListener{
                            if (it.hasChild(favouriteMusician.id)){
                                userFavouritesReference.child(fireAuthReference.currentUser!!.uid).child(favouriteMusician.id).removeValue()
                            } else {
                                fireAuthReference.currentUser?.let { it1 ->
                                    userFavouritesReference.child(it1.uid).child(favouriteMusician.id).setValue(favouriteUser).addOnSuccessListener {
                                        Toast.makeText(context, "Now this user is in your favourites!", Toast.LENGTH_SHORT).show()
                                    }.addOnFailureListener {
                                        Toast.makeText(context, "Error :(", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        })
                    }
                    true
                }
                else -> {
                    Toast.makeText(context, "Error :(", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
        popupMenu.inflate(R.menu.main_screen_button_menu)

        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception) {
            Log.i("Icon load", "Failed to load icon", e)
        } finally {

            fireAuthReference.currentUser?.let { it1 ->
                userFavouritesReference.child(it1.uid).addListenerForSingleValueEvent(AppValueEventListener{
                    if (it.hasChild(favouriteMusician.id)){
                        popupMenu.menu.findItem(R.id.add_to_favourites).setIcon(R.drawable.ic_heart_red)
                    }
                })
            }
            popupMenu.show()
        }
    }
}