package com.whiterabbit.base.extension

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore


fun Uri.getRealPathFromURI(context: Context): String? {
    var cursor: Cursor? = null
    try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(this, proj, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    } catch (e:Exception) {
        e.printStackTrace()
    }finally {
        if (cursor != null) {
            cursor.close()
        }
    }
    return null
}