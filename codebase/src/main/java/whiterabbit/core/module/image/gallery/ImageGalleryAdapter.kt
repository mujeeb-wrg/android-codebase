package com.whiterabbit.base.module.image.gallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ImageGalleryAdapter(fragmentManager: androidx.fragment.app.FragmentManager
                          , private val imageUrls: ArrayList<String>): androidx.fragment.app.FragmentPagerAdapter(fragmentManager){

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return ImageFragment.newInstance(imageUrls[position])
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

}