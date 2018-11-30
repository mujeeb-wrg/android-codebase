package com.whiterabbit.base.extension.view.acitivity

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat


fun Activity.hasPermission(permission:String): Boolean{
    return ContextCompat
            .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}


fun Activity.permissionRequired(permission:String): Boolean{
    return !hasPermission(permission)
}

