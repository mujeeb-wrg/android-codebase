package whiterabbit.codebase.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import whiterabbit.com.codebase.R
import whiterabbit.codebase.ui.activity.WrgActivity

/**
 * Created by mujeeb on 27/04/18.
 */


abstract class WrgFragment: Fragment(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
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

    abstract fun initUi()
    abstract fun registerUiEvents()

    open fun initActionBar() {
        getTitle()?.let {title ->
            activity?.title = title
        }
    }

    open fun callWebservice() {

    }

    open fun setAdapter() {

    }

    open fun getTitle():String?{
        return null
    }

    fun switchFragment(fragment: WrgFragment, resId:Int = R.id.container, tag:String? = null){
        (activity as? WrgActivity)?.let {
            it.navigateTo(fragment, resId, tag)
        }
    }

}