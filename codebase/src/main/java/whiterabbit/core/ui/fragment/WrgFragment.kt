package whiterabbit.core.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.util.Log
import whiterabbit.com.codebase.R
import whiterabbit.core.ui.activity.WrgActivity

/**
 * Created by mujeeb on 27/04/18.
 */


abstract class WrgFragment: androidx.fragment.app.Fragment(){

    private val broadCastReceivers = ArrayList<BroadcastReceiver>()

    abstract fun initUi()
    abstract fun registerUiEvents()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
        view?.isClickable = true
    }

    private fun setup(){
        requestPermission()
        initActionBar()
        initUi()
        setData()
        registerUiEvents()
        setAdapter()
        callWebservice()
    }

    open fun requestPermission() {

    }

    open fun setData() {

    }


    open fun initActionBar() {

    }

    open fun callWebservice() {

    }

    open fun setAdapter() {

    }

    open fun getTitle():String?{
        return null
    }

    fun switchFragment(fragment: WrgFragment, resId:Int = R.id.container, tag:String? = null
                       , clearBackStack:Boolean = false){
        (activity as? WrgActivity)?.let {
            it.navigateTo(fragment, resId, tag, clearBackStack)
        }
    }

    open fun isBackButtonEnabled(): Boolean{
        return true
    }

    override fun onResume() {
        super.onResume()
        initTitle()
    }

    open fun initTitle() {
        getTitle()?.let {title ->
            activity?.title = title
        }
    }

    fun registerForBroadcast(action:String, onReceive:(intent: Intent?)->Unit) {
        val context = activity ?: return
        val receiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                onReceive.invoke(intent)
            }
        }
        broadCastReceivers.add(receiver)
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(context).registerReceiver(receiver, IntentFilter(action))
    }

    fun sendBroadcastFor(action:String, bundle: Bundle? = null){
        Log.v("Broadcast ------->", action)
        val context = activity ?: return
        val intent = Intent(action)
        bundle?.let {
            intent.putExtras(bundle)
        }
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        val context = activity ?: return
        for(receiver in broadCastReceivers){
            androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
        }
    }

}