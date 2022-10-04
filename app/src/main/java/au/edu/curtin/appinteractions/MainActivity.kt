package au.edu.curtin.appinteractions

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callFragment = CallFragment()
        val locationFragment = LocationFragment()
        val cameraFragment = CameraFragment()
        val contactFragment = ContactFragment()
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        checkCallPermissions()
        setCurrentFragment(callFragment)

        bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_call -> {
                    checkCallPermissions()
                    setCurrentFragment(callFragment)
                }
                R.id.nav_location -> {
                    checkLocationPermissions()
                    setCurrentFragment(locationFragment)
                }
                R.id.nav_camera -> {
                    checkCameraPermissions()
                    checkStoragePermissions()
                    setCurrentFragment(cameraFragment)
                }
                R.id.nav_contact -> {
                    checkContactPermissions()
                    setCurrentFragment(contactFragment)
                }
            }
            true
        }

    }

    private fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 102)
        }


    }

    private fun checkCallPermissions() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 101)
        }
    }

    private fun checkContactPermissions() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), 103)
        }
    }

    private fun checkCameraPermissions() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 104)
        }
    }

    private fun checkStoragePermissions() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 105)
        }
    }



    private fun setCurrentFragment(Fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, Fragment)
            commit()
        }
}