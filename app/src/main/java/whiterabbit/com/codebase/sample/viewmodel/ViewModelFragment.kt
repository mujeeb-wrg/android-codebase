package whiterabbit.com.codebase.sample.viewmodel


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.whiterabbit.base.ui.fragment.viewmodel.WrgViewModelFragment
import kotlinx.android.synthetic.main.fragment_view_model.*

import whiterabbit.com.codebase.sample.R
import whiterabbit.com.codebase.sample.databinding.FragmentViewModelBinding

/**
 * A simple [Fragment] subclass.
 *
 */
class ViewModelFragment : WrgViewModelFragment<ViewModel, FragmentViewModelBinding>() {

    override fun getViewId(): Int {
        return  R.layout.fragment_view_model
    }

    override fun getViewModelClass(): Class<ViewModel> {
        return ViewModel::class.java
    }

    override fun onViewModelAvailable(viewModel: ViewModel) {
        binding.viewModel = viewModel
    }

    override fun registerUiEvents() {
        btnIncrease.setOnClickListener {


        }

        btnIncrease.setOnClickListener {


        }
    }
}
