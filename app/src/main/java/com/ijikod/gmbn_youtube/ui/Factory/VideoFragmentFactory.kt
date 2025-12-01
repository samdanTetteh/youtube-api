package com.ijikod.gmbn_youtube.ui.Factory

import androidx.fragment.app.FragmentFactory
import com.ijikod.gmbn_youtube.ui.Fragments.DetailsFragment

class VideoFragmentFactory : FragmentFactory(){

    override fun instantiate(
        classLoader: ClassLoader,
        className: String) = when(className){
            DetailsFragment::class.java.name -> DetailsFragment()
            else -> {
                super.instantiate(classLoader, className)
            }
        }
}