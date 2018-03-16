package org.common.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import org.common.R
import org.common.adapter.ContactAdapter
import org.common.databinding.ReadContactActivityBinding
import org.common.library.activity.BaseActivity
import org.common.model.AddressBook
import org.common.viewmodel.ReadContactViewModel

/**
 * Created by peter on 2018/3/15.
 */
class ReadContactActivity : BaseActivity(), ReadContactViewModel.ReadContactInterface {


    lateinit var viewModel: ReadContactViewModel
    lateinit var binding: ReadContactActivityBinding
    private val adapter: ContactAdapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.read_contact_activity)
        viewModel = ReadContactViewModel(this, this)
        binding.viewModel = viewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.recyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        viewModel.getContact()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
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

    override fun gotContact(addressBooks: ArrayList<AddressBook>) {
        adapter.contacts = addressBooks
        adapter.notifyDataSetChanged()
    }
}