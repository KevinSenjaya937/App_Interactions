package au.edu.curtin.appinteractions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import org.w3c.dom.Text

class ContactFragment : Fragment() {

    private lateinit var idText: TextView
    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var phoneText: TextView
    private lateinit var getContactBtn: Button
    private val CONTACT_PICKUP_CODE = 2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data

                data?.let {
                    val cursor = context?.contentResolver?.query(
                        data,
                        null,
                        null,
                        null,
                        null
                    )

                    cursor?.let {
                        if (it.moveToFirst()) {
                            idText.text = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID)).toString()
                            nameText.text = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).toString()

                        }

                    }
                }
            }
        }

        getContactBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        }
    }


}