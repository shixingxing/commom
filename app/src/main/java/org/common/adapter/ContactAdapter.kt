package org.common.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.common.R
import org.common.databinding.ContactItemLayoutBinding
import org.common.model.AddressBook
import org.common.viewmodel.ContactViewModel

/**
 * Created by peter on 2018/3/16.
 */
class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    var contacts: ArrayList<AddressBook>
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<ContactItemLayoutBinding>(inflater, R.layout.contact_item_layout, parent, false)
        return ContactHolder(binding)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bindContact(contacts[position])
    }

    class ContactHolder : RecyclerView.ViewHolder {
        val binding: ContactItemLayoutBinding

        constructor(binding: ContactItemLayoutBinding) : super(binding.root) {
            this.binding = binding
        }

        fun bindContact(contact: AddressBook) {

            if (binding.viewModel == null) {
                binding.viewModel = ContactViewModel(itemView.context, contact)
            } else {
                binding.viewModel!!.addressBook = contact
            }
        }
    }

    init {
        contacts = ArrayList()
    }
}