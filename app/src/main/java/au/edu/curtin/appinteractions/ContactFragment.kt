package au.edu.curtin.appinteractions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class ContactFragment : Fragment() {

    private lateinit var idText: TextView
    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var phoneText: TextView
    private lateinit var getContactBtn: Button

    @SuppressLint("Range")
    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val contactUri: Uri? = it.data?.data

            val cursor = contactUri?.let { uri ->
                requireActivity().contentResolver.query(
                    uri, null, null, null, null)
            }

            val cursor2: Cursor?
            val cursor3: Cursor?

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val idResults = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    val idResultHold =idResults.toInt()

                    idText.text = contactID
                    nameText.text = contactName

                    if (idResultHold == 1) {
                        cursor2 = requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "+contactID,
                        null,
                        null
                        )
                        while (cursor2!!.moveToNext()) {
                            val contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            phoneText.text = contactNumber
                        }
                    }

                    cursor3 = requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "+contactID,
                    null,
                    null
                    )

                    while (cursor3!!.moveToNext()) {
                        val contactEmail = cursor3.getString(cursor3.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        emailText.text = contactEmail
                    }

                }


            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)


    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idText = view.findViewById(R.id.idText)
        nameText = view.findViewById(R.id.nameText)
        emailText = view.findViewById(R.id.emailText)
        phoneText = view.findViewById(R.id.phoneNumberText)
        getContactBtn = view.findViewById(R.id.searchContactBtn)


        getContactBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            resultLauncher.launch(intent)
        }
    }




}