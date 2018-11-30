package whiterabbit.core.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import whiterabbit.com.codebase.R

/**
 * Created by mujeeb on 27/04/18.
 */


abstract class WrgActivity: AppCompatActivity(){


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setup()
    }

    private fun setup(){
        setData()
        initActionBar()
        initUi()
        registerUiEvents()
        setAdapter()
    }

    open fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(showActionBarBackButton())
    }

    abstract fun initUi()

    abstract fun registerUiEvents()

    abstract fun setData()

    open fun setAdapter() {

    }

    open fun showActionBarBackButton(): Boolean{
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount <= 1){
                    onBackPressed()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    open fun navigateTo(fragment: Fragment, resId:Int = R.id.container, tag:String? = null){
        supportFragmentManager.beginTransaction()
                .add(resId,fragment,tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(tag)
                .commit()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount <= 1){
            finish()
        }else{
            supportFragmentManager.popBackStackImmediate()
        }
    }


}