package com.whiterabbit.base.extension.view.fragment

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import com.whiterabbit.base.extension.view.acitivity.hasPermission
import com.whiterabbit.base.extension.view.acitivity.permissionRequired


fun Fragment.hideKeyboard(){
    if (view != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getView()?.getWindowToken(), 0)
    }
}

fun Fragment.hasPermission(permission:String): Boolean{
    return activity!!.hasPermission(permission)
}

fun Fragment.permissionRequired(permission:String): Boolean{
    return activity!!.permissionRequired(permission)
}

fun Fragment.requestPermissionIfRequired(permission:String): Boolean{
    return if(activity!!.permissionRequired(permission)){
        requestPermissions(arrayOf(permission), 10)
        true
    }else{
        false
    }
}
