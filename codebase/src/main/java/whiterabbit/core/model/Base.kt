package whiterabbit.core.model

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.widget.Toast


data class WrgApiStatus(val code:Int,val message: String?){

    val isSuccess: Boolean get() = code == 200

    fun showMessage(context: Context?){
        context?.let {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showAlert(context: Activity?, title:String,completion: (() -> Unit)? = null ){
        context?.let {
            AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK") { _, _ ->
                        completion?.invoke()
                    }
                    .show()
        }
    }

    fun isSuccess(context:Context?): Boolean{
        context?.let {
            if(!isSuccess){
                showMessage(context)
                handleError(context, code)
            }
        }

        return isSuccess
    }

    fun isSuccess(fragment: androidx.fragment.app.Fragment?): Boolean{
        return isSuccess(fragment?.activity)
    }

    fun handleError(context: Context?, code: Int) {

    }
}


data class SimpleResponse(val status: WrgApiStatus)

open class ApiResponse(val status: WrgApiStatus)

data class GenericResponse(val status: WrgApiStatus,val data:String)
