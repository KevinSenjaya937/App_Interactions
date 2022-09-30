package au.edu.curtin.appinteractions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class LocationFragment : Fragment() {

    private lateinit var getLocationBtn: Button
    private lateinit var longitudeText: EditText
    private lateinit var latitudeText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLocationBtn = view.findViewById(R.id.showLocationBtn)
        longitudeText = view.findViewById(R.id.longitudeInput)
        latitudeText = view.findViewById(R.id.latitudeInput)

        getLocationBtn.setOnClickListener {
            val latitude = latitudeText.text.toString()
            val longitude = longitudeText.text.toString()

            if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
                val gmnIntentUri = Uri.parse("geo:$latitude, $longitude")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmnIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }

}