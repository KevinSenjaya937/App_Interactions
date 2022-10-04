package au.edu.curtin.appinteractions

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


class CameraFragment : Fragment() {

    private lateinit var cameraImage: ImageView
    private lateinit var takePicBtn: Button
    private lateinit var grayscaleBtn: Button
    private lateinit var imageUri: Uri

    private val takePhotoResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            cameraImage.setImageURI(imageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraImage = view.findViewById(R.id.cameraImage)
        takePicBtn = view.findViewById(R.id.takePicBtn)
        grayscaleBtn = view.findViewById(R.id.grayscaleBtn)

        takePicBtn.setOnClickListener {
            takePhoto()
        }

        grayscaleBtn.setOnClickListener {
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            val filter = ColorMatrixColorFilter(colorMatrix)
            cameraImage.colorFilter = filter
        }
    }

    private fun takePhoto() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        imageUri = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        takePhotoResult.launch(intent)
    }
}