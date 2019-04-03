package com.whiterabbit.base.extension.view.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import com.whiterabbit.base.extension.view.acitivity.hasPermission
import com.whiterabbit.base.extension.view.acitivity.permissionRequired


fun androidx.fragment.app.Fragment.hideKeyboard(){
    if (view != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getView()?.getWindowToken(), 0)
    }
}

fun androidx.fragment.app.Fragment.hasPermission(permission:String): Boolean{
    return activity!!.hasPermission(permission)
}

fun androidx.fragment.app.Fragment.permissionRequired(permission:String): Boolean{
    return activity!!.permissionRequired(permission)
}

fun androidx.fragment.app.Fragment.requestPermissionIfRequired(permission:String): Boolean{
    return if(activity!!.permissionRequired(permission)){
        requestPermissions(arrayOf(permission), 10)
        true
    }else{
        false
    }
}
