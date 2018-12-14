package whiterabbit.core.ui.view.progress

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import com.whiterabbit.base.ui.fragment.viewmodel.WrgViewModel

class WrgSwipeToRefreshLayout: SwipeRefreshLayout{

    private var progressData: ProgressLayoutLiveData? = null

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

    fun setViewModel(progressLayoutLiveData: ProgressLayoutLiveData
                     , lifecycleOwner: LifecycleOwner){
        this.progressData = progressLayoutLiveData
        progressLayoutLiveData.isInProgress.observe(lifecycleOwner
                , Observer<Boolean> { it ->
            it?.let {
                if(!it && isRefreshing)
                    progressLayoutLiveData?.isSwipeToRefreshInProgress.value = false
                    isRefreshing = false
            }
        })

        progressLayoutLiveData.isError.observe(lifecycleOwner
                , Observer<Boolean> {it ->
            it?.let {isError ->
                if(isError){
                    progressLayoutLiveData?.isSwipeToRefreshInProgress.value = false
                    isRefreshing = false
                }
            }
        })
    }

}