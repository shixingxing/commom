package org.common.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.View
import org.common.R
import org.common.library.viewmodel.MyObservable
import org.common.model.AddressBook


/**
 * Created by peter on 2018/3/15.
 */
class ReadContactViewModel : MyObservable {

    private val PERMISSIONS_REQUEST_READ_CONTACTS = 1122

    private val readContactInterface: ReadContactInterface

    constructor(context: Context?, listener: ReadContactInterface) : super(context) {

        readContactInterface = listener

    }

    fun getContact() {
        if (context?.let { ActivityCompat.checkSelfPermission(it, android.Manifest.permission.READ_CONTACTS) } == PackageManager.PERMISSION_GRANTED) {

            //got permission
            readContactInterface.gotContact(readContact())
        } else {
            //not got permission
            requestPermission()
        }
    }

    override fun destroy() {
        super.destroy()
    }

    private fun requestPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
                        Manifest.permission.READ_CONTACTS)) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(R.string.not_have_permission_to_read_contact)
            alertDialog.setPositiveButton(R.string.ok, { _, _ -> goSetting() })
            alertDialog.setNegativeButton(R.string.cancel, null)
            alertDialog.create().show()

            readContactInterface.contactPermissionDenied()
        } else {

            ActivityCompat.requestPermissions(context as Activity,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    PERMISSIONS_REQUEST_READ_CONTACTS)
        }
    }

    private fun goSetting() {
        val intent = Intent()
        val activity = context
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(intent)
    }

    public fun onClickErrorMessage(view: View) {
        requestPermission()
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    readContactInterface.contactPermissionGranted()

                    readContactInterface.gotContact(readContact())

                } else {

                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setMessage(R.string.not_have_permission_to_read_contact)
                    alertDialog.setPositiveButton(R.string.ok, { _, _ -> goSetting() })
                    alertDialog.setNegativeButton(R.string.cancel, null)
                    alertDialog.create().show()

                    readContactInterface.contactPermissionDenied()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    private fun readContact(): ArrayList<AddressBook> {
        val addressBooks: ArrayList<AddressBook> = ArrayList()
        val contentResolver = context.contentResolver
        var c: Cursor? = null
        try {
            if (ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                return addressBooks
            }
            val projection = arrayOf(ContactsContract.Data.LOOKUP_KEY, ContactsContract.Data.DATA2, ContactsContract.Data.DATA3)
            val where = ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'"
            c = contentResolver.query(ContactsContract.Data.CONTENT_URI, projection, where, null, null)

            if (c != null) {
                var lookupKey: String
                var firstName: String?
                var lastName: String?
                val lookupKeyIndex = c.getColumnIndexOrThrow(ContactsContract.Data.LOOKUP_KEY)
                val firstNameIndex = c.getColumnIndexOrThrow(ContactsContract.Data.DATA2)
                val lastNameIndex = c.getColumnIndexOrThrow(ContactsContract.Data.DATA3)
                while (c.moveToNext()) {

                    lookupKey = c.getString(lookupKeyIndex)
                    firstName = c.getString(firstNameIndex)
                    lastName = c.getString(lastNameIndex)


                    val emailC = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS),
                            ContactsContract.CommonDataKinds.Email.LOOKUP_KEY + " = ?",
                            arrayOf(lookupKey), null)

                    val book = AddressBook()
                    book.firstName = firstName
                    book.lastName = lastName
                    val emails = ArrayList<String>()
                    if (emailC != null) {
                        while (emailC.moveToNext()) {
                            val email = emailC.getString(emailC.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))
                            emails.add(email)
                        }
                    }

                    book.emails = emails
                    addressBooks.add(book)
                    emailC!!.close()

                }
                c!!.close()

            }

        } catch (e: Exception) {
            e.printStackTrace()
            c?.close()
        }

        return addressBooks
    }

    interface ReadContactInterface {
        fun contactPermissionDenied()
        fun contactPermissionGranted()
        fun gotContact(addressBooks: ArrayList<AddressBook>)
    }
}