package com.whiterabbit.base.extension.view.widget

import android.widget.EditText
import android.widget.Toast
import com.whiterabbit.base.extension.WrgAppController


fun EditText.isEmpty(message:String?): Boolean{
    if(text.isEmpty()){
        requestFocus()
        show(message)
    }
    return text.isEmpty()
}

fun show(message: String?){
    message.let {
        Toast.makeText(WrgAppController.instance, it, Toast.LENGTH_SHORT).show()
    }
}