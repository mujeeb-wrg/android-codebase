package com.whiterabbit.base.module.image.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_image_gallery.*
import whiterabbit.codebase.ui.activity.WrgActivity
import whiterabbit.com.codebase.R

class ImageGalleryActivity : WrgActivity() {

    lateinit var images:ArrayList<String>

    companion object {
        fun intent(context: Context, images:ArrayList<String>, initialPosition:Int):Intent{
            val intent = Intent(context, ImageGalleryActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_URLS, images)
            intent.putExtra(EXTRA_INITIAL_IMAGE_POSITION, initialPosition)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_gallery)
    }

    override fun initUi() {

    }

    override fun registerUiEvents() {

    }

    override fun setData() {
        intent.getStringArrayListExtra(EXTRA_IMAGE_URLS)?.let {
            images = it
        }?: run {
            throw (Exception(".........Images not found......"))
        }
    }

    override fun setAdapter() {
        pager.adapter = ImageGalleryAdapter(supportFragmentManager, images)
        pager.currentItem = intent.getIntExtra(EXTRA_INITIAL_IMAGE_POSITION, 0)
    }
}
