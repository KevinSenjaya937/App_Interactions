package au.edu.curtin.appinteractions

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest


class CallFragment : Fragment() {

    private lateinit var callBtn: Button
    private lateinit var phoneNumberText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callBtn = view.findViewById(R.id.callBtn)
        phoneNumberText = view.findViewById(R.id.phoneNumberInput)

        callBtn.setOnClickListener {
            val phoneNumber = phoneNumberText.text.toString()

            if (phoneNumber.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(callIntent)
            }
        }
    }

}