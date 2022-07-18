package com.tms.android.cosinging.MainScreen.Fragments.Musician

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.tms.android.cosinging.R
import com.tms.android.cosinging.MainScreen.Data.Musician
import com.tms.android.cosinging.MainScreen.MainActivity
import com.tms.android.cosinging.MainScreen.ViewModels.MusiciansViewModel
import com.tms.android.cosinging.MainScreen.ViewModels.UserViewModel

class ListOfMusicians: Fragment() {

    private val musicianListViewModel: MusiciansViewModel by lazy {
        ViewModelProviders.of(this).get(MusiciansViewModel::class.java)
    }

    private lateinit var userHashMap: HashMap<String, String>

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

        updateUI()

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userHashMap = (activity as MainActivity?)!!.userHashMap
        userAvatar.load(userHashMap["photoLink"]){
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }


    private inner class ListOfMusiciansHolder(view: View): RecyclerView.ViewHolder(view){

        private lateinit var musician: Musician
        private val name: TextView = itemView.findViewById(R.id.musician_list_name)
        private val profession: TextView = itemView.findViewById(R.id.musician_list_profession)
        private val avatar: ImageView = itemView.findViewById(R.id.musician_list_avatar)
        private val favouriteImg: ImageView = itemView.findViewById(R.id.musician_list_favourite)
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
                Navigation.findNavController(requireView()).navigate(R.id.action_listOfMusicians_to_musicianCardFullScreen)
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
            holder.bind(musician)
        }

        override fun getItemCount(): Int = list.size
    }

    companion object{
        fun newInstance(): ListOfMusicians = ListOfMusicians()
    }

    private fun updateUI(){
        val musicianList = musicianListViewModel.musicianList
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
}