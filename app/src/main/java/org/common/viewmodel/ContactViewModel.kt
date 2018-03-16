package org.common.viewmodel

import android.content.Context
import org.common.library.viewmodel.MyObservable
import org.common.model.AddressBook

/**
 * Created by peter on 2018/3/16.
 */
class ContactViewModel : MyObservable {

     var addressBook: AddressBook

    constructor(context: Context, addressBook: AddressBook) : super(context) {
        this.addressBook = addressBook
    }

    public fun getName(): String {
        return addressBook.firstName + " " + addressBook.lastName
    }

    public fun getEmailAddress(): String {
        var email = ""
        addressBook.emails?.forEach { e ->
            email += e
        }
        return email
    }
}