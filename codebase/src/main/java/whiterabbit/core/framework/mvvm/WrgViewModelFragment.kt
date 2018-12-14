package com.whiterabbit.base.ui.fragment.viewmodel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import whiterabbit.com.codebase.R
import whiterabbit.core.model.WrgApiStatus
import whiterabbit.core.ui.fragment.WrgFragment
import whiterabbit.core.ui.view.progress.NetworkLayout
import whiterabbit.core.ui.view.progress.WrgSwipeToRefreshLayout

abstract class WrgViewModelFragment<VM: WrgViewModel, DB: ViewDataBinding>: WrgFragment(){

    lateinit var binding: DB
    lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil
                .inflate(inflater, getViewId()
                        , container, false)
        return binding.root
    }

    abstract fun getViewId(): Int

    abstract fun getViewModelClass(): Class<VM>

    abstract fun onViewModelAvailable(viewModel: VM)

    override fun setData() {
        super.setData()
        viewModel = ViewModelProviders.of(this).get(getViewModelClass())
        binding.setLifecycleOwner(this)
        attachViewModelWithProgressViews()
        onViewModelAvailable(viewModel)

        initiateEventListener()

        initiateDataListener()
    }

    open fun initiateDataListener() {

    }

    private fun initiateEventListener() {
        viewModel.apiResponseEvent.observe(this, Observer<ApiResponseEvent> {
            it?.let {
                onApiResponse(it.tag, it.status)
            }
        })

        viewModel.messageEvent.observe(this, Observer<MessageEvent> {
            it?.let {
                onMessageEvent(it)
            }
        })

        viewModel.navigationEvent.observe(this, Observer<String> {
            it?.let {
                onNavigationEvent(it)
            }
        })
    }

    open fun onNavigationEvent(event: String) {

    }

    open fun onMessageEvent(message: MessageEvent) {

    }

    open fun onApiResponse(tag: String, status: WrgApiStatus) {

    }

    fun attachViewModelWithProgressViews() {
        view?.findViewById<NetworkLayout>(R.id.networkLayout)
                ?.setViewModel(viewModel, this)
        view?.findViewById<WrgSwipeToRefreshLayout>(R.id.swipeRefreshLayout)
                ?.setViewModel(viewModel, this)
    }


    override fun initUi() {

    }

    override fun registerUiEvents() {

    }

}

