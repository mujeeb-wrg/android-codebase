package whiterabbit.com.codebase.sample.progress


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import whiterabbit.com.codebase.sample.R
import whiterabbit.core.ui.fragment.WrgFragment


class ProgressLayoutFragment : WrgFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_layout, container, false)
    }

    override fun initUi() {

    }

    override fun registerUiEvents() {

    }

    override fun callWebservice() {

    }

}
