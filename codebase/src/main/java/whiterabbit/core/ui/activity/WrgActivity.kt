package whiterabbit.core.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import whiterabbit.com.codebase.R
import whiterabbit.core.ui.fragment.WrgFragment

/**
 * Created by mujeeb on 27/04/18.
 */


abstract class WrgActivity: AppCompatActivity(), androidx.fragment.app.FragmentManager.OnBackStackChangedListener {

    private val broadCastReceivers = ArrayList<BroadcastReceiver>()

    //<editor-fold desc="Lifecycle">
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setup()

        supportFragmentManager?.addOnBackStackChangedListener(this)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount <= 1){
            finish()
        }else{
            (supportFragmentManager.fragments.last() as? WrgFragment)?.run {
                if(!isBackButtonEnabled())
                    return
            }
            supportFragmentManager.popBackStackImmediate()
            invalidateOptionsMenu()
            (supportFragmentManager.fragments.last() as? WrgFragment)?.run {
                initTitle()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceivers()
    }
    //</editor-fold>


    //<editor-fold desc="Factory">
    private fun setup(){
        setData()
        initActionBar()
        initUi()
        registerUiEvents()
        setAdapter()
        callWebService()
    }

    open fun callWebService() {

    }

    abstract fun initUi()

    abstract fun registerUiEvents()

    open fun setData(){

    }

    open fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(showActionBarBackButton())
    }

    open fun setAdapter() {

    }

    open fun showActionBarBackButton(): Boolean{
        return false
    }
    //</editor-fold>


    //<editor-fold desc="Menu">
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //</editor-fold>

    //<editor-fold desc="Fragment">
    open fun navigateTo(fragment: androidx.fragment.app.Fragment, resId:Int = R.id.container, tag:String? = null
                        , clearBackStack:Boolean = false){
        if(clearBackStack)
            supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
                .add(resId,fragment,tag)
                .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(tag)
                .commit()
        invalidateOptionsMenu()
    }


    override fun onBackStackChanged() {
        if(supportFragmentManager.fragments.count() < 1)
            return
        (supportFragmentManager.fragments.last() as? WrgFragment)?.run {
            initTitle()
        }
    }

    //</editor-fold>


    //<editor-fold desc="Broadcast">
    fun registerForBroadcast(action:String, onReceive:(intent: Intent?)->Unit) {
        val receiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                onReceive?.invoke(intent)
            }
        }
        broadCastReceivers.add(receiver)
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver, IntentFilter(action))
    }


    fun sendBroadcastFor(action:String, bundle: Bundle? = null){
        Log.v("Broadcast ------->", action)
        val intent = Intent(action)
        bundle?.let {
            intent.putExtras(bundle)
        }
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun unregisterReceivers(){
        for(receiver in broadCastReceivers){
            androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
        }
    }
    //</editor-fold>




}