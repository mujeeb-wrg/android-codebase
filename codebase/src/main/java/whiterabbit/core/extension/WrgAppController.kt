package com.whiterabbit.base.extension

import android.app.Application


open class WrgAppController: Application(){

    companion object {
        var instance: WrgAppController? = null
    }

    override fun onCreate() {
        super.onCreate()
        WrgAppController.instance = this
    }


}