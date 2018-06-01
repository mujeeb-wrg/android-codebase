package com.whiterabbit.base.module.image.gallery

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ImageGalleryAdapter(fragmentManager: FragmentManager
                          , private val imageUrls: ArrayList<String>): FragmentPagerAdapter(fragmentManager){

    override fun getItem(position: Int): Fragment {
        return ImageFragment.newInstance(imageUrls[position])
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

}