package org.common.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import org.common.R
import org.common.databinding.ReadContactActivityBinding
import org.common.library.activity.BaseActivity
import org.common.viewmodel.ReadContactViewModel

/**
 * Created by peter on 2018/3/15.
 */
class ReadContactActivity : BaseActivity(), ReadContactViewModel.ReadContactInterface {


    lateinit var viewModel: ReadContactViewModel
    lateinit var binding: ReadContactActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.read_contact_activity)
        viewModel = ReadContactViewModel(this, this)
        binding.viewModel = viewModel

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun contactPermissionDenied() {
        binding.viewFlipper.displayedChild = 1
    }

    override fun contactPermissionGranted() {
        binding.viewFlipper.displayedChild = 0
    }
}