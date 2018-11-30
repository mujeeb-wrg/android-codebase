package com.whiterabbit.base.extension.view

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.animation.AnimationUtils
import com.whiterabbit.dapit.R


fun View.animateErrorFeedback(){
//        Animating
    val animShake = AnimationUtils.loadAnimation(context, R.anim.animation_invalid_key_typed);
    startAnimation(animShake);

//        Vibrating
    (context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            it.vibrate(VibrationEffect.createOneShot(50,10))
        }else {
            it.vibrate(50)
        }
    }
}