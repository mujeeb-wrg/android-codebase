package whiterabbit.core.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import whiterabbit.com.codebase.R
import whiterabbit.core.ui.fragment.WrgFragment

/**
 * Created by mujeeb on 27/04/18.
 */


abstract class WrgActivity: AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

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
    open fun navigateTo(fragment: Fragment, resId:Int = R.id.container, tag:String? = null
                        ,clearBackStack:Boolean = false){
        if(clearBackStack)
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
                .add(resId,fragment,tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
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
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver, IntentFilter(action))
    }


    fun sendBroadcastFor(action:String, bundle: Bundle? = null){
        Log.v("Broadcast ------->", action)
        val intent = Intent(action)
        bundle?.let {
            intent.putExtras(bundle)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun unregisterReceivers(){
        for(receiver in broadCastReceivers){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
        }
    }
    //</editor-fold>




}