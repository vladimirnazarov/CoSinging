package com.tms.android.cosinging.mainScreen.ViewModels

import androidx.lifecycle.ViewModel
import com.tms.android.cosinging.mainScreen.Data.Musician

class UserViewModel: ViewModel() {

    val musicianList = mutableListOf<Musician>()

    private val linkImg = "https://i.pinimg.com/originals/3f/e9/46/3fe9467eccd40719db3707ceafaa2725.jpg"
    private val name = "Егор Плащинский"
    private val profession = "Гитарист"

    init {
        for(index in 0..100){
            val musician = Musician(id = index)
            musician.name = this.name
            musician.profession = this.profession
            musician.photoLink = linkImg

            musicianList += musician
        }
    }
}