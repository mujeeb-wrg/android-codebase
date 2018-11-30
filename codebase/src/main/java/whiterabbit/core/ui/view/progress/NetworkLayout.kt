package whiterabbit.core.ui.view.progress

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.whiterabbit.base.ui.fragment.viewmodel.WrgViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import whiterabbit.com.codebase.R


interface NetworkLayoutListener<T>{
    fun onResponse(body: T?)
    fun onRetry()
}

class NetworkLayoutLiveData{
    var isInProgress = MutableLiveData<Boolean>()
    var isError = MutableLiveData<Boolean>()
    var isSwipeToRefreshInProgress = MutableLiveData<Boolean>()
    init {
        isInProgress.value = false
        isError.value = false
        isSwipeToRefreshInProgress.value = false
    }
}

class NetworkLayout: FrameLayout {

    lateinit var networkView:View
    lateinit var progressBar:ProgressBar
    lateinit var btnRetry:Button
    lateinit var viewRetry:View

    private var networkLayoutLiveData: NetworkLayoutLiveData? = null

    val isInProgress:Boolean
        get() {return  progressBar.visibility == View.VISIBLE}


    private var onRetry: (()-> Unit)? =null


    constructor(context: Context?) : super(context){
        initView(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView(attrs, 0)
    }

    constructor(context: Context?
                , attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        initView(attrs, defStyleAttr)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?
                , attrs: AttributeSet?
                , defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr
            , defStyleRes){
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyle: Int){
        networkView = LayoutInflater.from(context).inflate(R.layout.layout_network_view
                , this, false)
        progressBar = networkView.findViewById(R.id.progressBar)
        btnRetry = networkView.findViewById(R.id.btnRetry)
        viewRetry = networkView.findViewById(R.id.flRetry)
        addView(networkView)

        networkView.visibility = View.GONE
        attrs?.let {
            val a = context.obtainStyledAttributes(
                    attrs, R.styleable.NetworkLayout, defStyle, 0)
            val backgroundColor = a.getInt(R.styleable.NetworkLayout_retryBackground
                    , Color.parseColor("#F2F2F2"))
            viewRetry.setBackgroundColor(backgroundColor)

            if(a.getBoolean(R.styleable.NetworkLayout_tint_enabled, false)) {
                networkView.setBackgroundColor(Color.parseColor("#55000000"))
            }else if(a.hasValue(R.styleable.NetworkLayout_networkViewBackground)){
                networkView.setBackgroundColor(a.getInt(R.styleable.NetworkLayout_networkViewBackground
                        , Color.parseColor("#F2F2F2")))
            }
        }
    }

    fun showProgress(){

        if(networkLayoutLiveData?.isSwipeToRefreshInProgress?.value == true){
            return
        }

        hideError()
        networkView.visibility = View.VISIBLE
        viewRetry.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        networkView.bringToFront()
    }

    fun hideProgress(){
        networkView.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    fun showNetworkError(retry: (()-> Unit)? =null){
        networkView.visibility = View.VISIBLE
        viewRetry.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        networkView.bringToFront()
        btnRetry.setOnClickListener {
            hideError()
            onRetry = onRetry?:retry
            this.onRetry?.invoke()
        }
    }

    fun hideError(){
        networkView.visibility = View.GONE
    }

    fun onRetry(retry:(()-> Unit)){
        this.onRetry = retry
    }

    fun<T> callback(swipeToRefreshLayout: SwipeRefreshLayout? = null
                    , success:(T)->Unit): Callback<T>{
        return  callback<T>(swipeToRefreshLayout = swipeToRefreshLayout
                , success = success, retry = null)
    }

    fun<T> callback(swipeToRefreshLayout: SwipeRefreshLayout? = null
                    , success:(T)->Unit, retry:(() -> Unit)?): Callback<T>{

        swipeToRefreshLayout?.let {
            if(!it.isRefreshing){
                showProgress()
            }
        }?:kotlin.run { showProgress() }

        return object :Callback<T>{
            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                swipeToRefreshLayout?.isRefreshing = false
                hideProgress()
                response?.body()?.let {
                    success.invoke(it)
                }?: kotlin.run {
                    showNetworkError()
                }
            }

            override fun onFailure(call: Call<T>?, t: Throwable?) {
                t?.printStackTrace()
                showNetworkError {
                    retry?.invoke()
                }
            }
        }
    }


    @Deprecated("Use callback(success:(T)->Unit, retry:(() -> Unit)?): Callback<T> instead")
    fun<T> callback(listener: NetworkLayoutListener<T>): Callback<T>{
        showProgress()
        return object :Callback<T>{
            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                hideProgress()
                listener.onResponse(response?.body())
            }

            override fun onFailure(call: Call<T>?, t: Throwable?) {
                t?.printStackTrace()
                showNetworkError {
                    listener.onRetry()
                }
            }
        }
    }



    //<editor-fold desc="Description">
    fun setViewModel(viewModel: WrgViewModel, lifecycleOwner: LifecycleOwner){
        setViewModel(viewModel.networkLayoutLiveData, lifecycleOwner)
    }


    fun setViewModel(networkLayoutLiveData: NetworkLayoutLiveData, lifecycleOwner: LifecycleOwner){
        this.networkLayoutLiveData = networkLayoutLiveData
        networkLayoutLiveData.isInProgress.observe(lifecycleOwner
                , Observer<Boolean> {it ->
            it?.let {
                if(it){
                    showProgress()
                }else{
                    hideProgress()
                }
            }
        })

        networkLayoutLiveData.isError.observe(lifecycleOwner
                , Observer<Boolean> {it ->
            it?.let {isError ->
                if(isError){
                    showNetworkError()
                }
            }
        })
    }
    //</editor-fold>

}