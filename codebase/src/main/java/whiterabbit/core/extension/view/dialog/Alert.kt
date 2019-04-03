package com.whiterabbit.base.extension.view.dialog

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog

val REQUEST_PICK_IMAGE = 10

class Alert(context: Context) : AlertDialog(context) {
    companion object {

    }
}

private fun imagePickerIntent(allowMultiple:Boolean = false): Intent {
    val intent = Intent()
    intent.action = Intent.ACTION_GET_CONTENT
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple);
    intent.type = "image/*"
    return intent
}

fun Alert.Companion.showImagePickerDialog(fragment: androidx.fragment.app.Fragment, allowMultiple:Boolean = false){
    fragment?.activity?.let {activity->
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Pick Image")
        builder.setItems(arrayOf("Gallery", "Camera")) { dialog, which ->
            when(which){
                0->{
                    val intent = imagePickerIntent(allowMultiple)
                    fragment.startActivityForResult(Intent.createChooser(intent
                            , "Select Picture"), REQUEST_PICK_IMAGE)
                }
                1->{

                }
            }
        }
        builder.show()
    }
}

fun Alert.Companion.showImagePickerDialog(activity: Activity, allowMultiple:Boolean = false){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Pick Image")
        builder.setItems(arrayOf("Gallery", "Camera")) { dialog, which ->
            when(which){
                0->{
                    val intent = imagePickerIntent(allowMultiple)
                    activity.startActivityForResult(Intent.createChooser(intent
                            , "Select Picture"), REQUEST_PICK_IMAGE)
                }
                1->{

                }
            }
        builder.show()
    }
}