package whiterabbit.com.codebase.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.whiterabbit.base.ui.fragment.viewmodel.WrgViewModel

class ViewModel: WrgViewModel() {

    val data =  MutableLiveData<Int>()

    init {
        data.value = 0
    }

    override fun callWebservice() {

    }

}