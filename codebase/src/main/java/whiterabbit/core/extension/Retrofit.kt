package com.whiterabbit.base.extension

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun<T> callback(success:(T?)->Unit):Callback<T>{
    return object:Callback<T>{
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            success.invoke(response?.body())
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {

        }
    }
}



fun<T> Callback<T>.onSuccess(block:(T?)->Unit):Callback<T>{
    return object:Callback<T>{
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            block.invoke(response?.body())
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {

        }
    }
}