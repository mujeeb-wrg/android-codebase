package com.whiterabbit.base.ui.fragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import whiterabbit.core.model.WrgApiStatus
import whiterabbit.core.ui.view.progress.ProgressLayoutLiveData


//class NavigationEvent(val type:String)

class ApiResponseEvent(val tag:String, val status:WrgApiStatus)

class MessageEvent(val type:MessageEventType = MessageEventType.TOAST
                   , title:String? = null
                   , message:String){
    enum class MessageEventType(){
        DIALOG, TOAST
    }
}


open abstract class WrgViewModel: ViewModel() {

    var navigationEvent = MutableLiveData<String>()
    var apiResponseEvent = MutableLiveData<ApiResponseEvent>()
    var messageEvent = MutableLiveData<MessageEvent>()

    var networkLayoutLiveData = ProgressLayoutLiveData()

    init {
        callWebservice()
    }

    abstract fun callWebservice()

    fun<T> callBack(tag:String? = null, onResult: (T) -> Unit): Callback<T>{

        networkLayoutLiveData.isError.value = false
        networkLayoutLiveData.isInProgress.value = true

        return object: Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                networkLayoutLiveData.isError.value = true
            }
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful){
                    response.body()?.let { body ->
                        networkLayoutLiveData.isInProgress.value = false
                        onResult.invoke(body)
                    }?: kotlin.run {
                        networkLayoutLiveData.isError.value = true
                    }
                }else
                    networkLayoutLiveData.isError.value = true
            }
        }
    }

    fun sendApiResponse(tag: String, status: WrgApiStatus){
        apiResponseEvent.value = ApiResponseEvent(tag, status)
    }

    fun sendNavigationEvent(event:String){
        navigationEvent.value = event
    }

    fun sendResponse(status: WrgApiStatus, navigationEvent:String){
        apiResponseEvent.value = ApiResponseEvent(navigationEvent, status)
        this@WrgViewModel.navigationEvent.value = navigationEvent
    }

}