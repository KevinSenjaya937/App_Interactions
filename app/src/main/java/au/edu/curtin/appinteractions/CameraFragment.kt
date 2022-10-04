package au.edu.curtin.appinteractions

import android.app.Activity
import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import java.io.File
import java.io.IOException


class CameraFragment : Fragment() {

    private lateinit var cameraImage: ImageView
    private lateinit var takePicBtn: Button
    private lateinit var grayscaleBtn: Button
    private lateinit var imageUri: Uri
    private lateinit var photoFile: File


    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            imageUri.let { uri ->
                cameraImage.setImageURI(uri)
            }
            var file = File(activity?.filesDir, "Images")
            if (!file.exists()) {
                file.mkdir()
            }
            file = File(file, "img.jpg")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            takeImage()
        }

        grayscaleBtn.setOnClickListener {
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0F)
            val filter = ColorMatrixColorFilter(colorMatrix)
            cameraImage.setColorFilter(filter)
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                imageUri = uri
                takeImageResult.launch(uri)
            }
        }
    }


    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", requireActivity().filesDir).apply {
            createNewFile()
        }

        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }
}