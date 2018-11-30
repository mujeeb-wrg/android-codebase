package whiterabbit.core.ui.view.progress

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import com.whiterabbit.base.ui.fragment.viewmodel.WrgViewModel

class WrgSwipeToRefreshLayout: SwipeRefreshLayout{

    private var progressData: NetworkLayoutLiveData? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun setRefreshing(refreshing: Boolean) {
        super.setRefreshing(refreshing)
    }

    fun onRefresh(block: ()->Unit){
        setOnRefreshListener {
            //        isRefreshing = false
            progressData?.isSwipeToRefreshInProgress?.value = true
            block.invoke()
        }
    }

    fun setViewModel(viewModel: WrgViewModel, lifecycleOwner: LifecycleOwner){
        setViewModel(viewModel.networkLayoutLiveData, lifecycleOwner)
    }

    fun setViewModel(networkLayoutLiveData: NetworkLayoutLiveData
                     , lifecycleOwner: LifecycleOwner){
        this.progressData = networkLayoutLiveData
        networkLayoutLiveData.isInProgress.observe(lifecycleOwner
                , Observer<Boolean> { it ->
            it?.let {
                if(!it && isRefreshing)
                    networkLayoutLiveData?.isSwipeToRefreshInProgress.value = false
                    isRefreshing = false
            }
        })

        networkLayoutLiveData.isError.observe(lifecycleOwner
                , Observer<Boolean> {it ->
            it?.let {isError ->
                if(isError){
                    networkLayoutLiveData?.isSwipeToRefreshInProgress.value = false
                    isRefreshing = false
                }
            }
        })
    }

}