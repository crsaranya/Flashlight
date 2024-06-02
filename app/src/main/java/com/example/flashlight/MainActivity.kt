package com.example.flashlight


import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService

class MainActivity : AppCompatActivity() {
    private var toggleButton: ImageButton? = null

    var hasCameraFlash: Boolean = false
    var flashOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        toggleButton = findViewById(R.id.toggle)

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        toggleButton!!.setOnClickListener {
            if (hasCameraFlash) {
                if (flashOn) {
                    flashOn = false
                    toggleButton!!.setImageResource(R.drawable.button_onoff_indicator_off)
                    flashLightOff()
                } else {
                    flashOn = true
                    toggleButton!!.setImageResource(R.drawable.button_onoff_indicator_on)
                    flashLightOn()
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "No flash available on your device",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun flashLightOn() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        try {
            assert(cameraManager != null)
            val cameraId = cameraManager!!.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, true)
            Toast.makeText(this@MainActivity, "FlashLight is ON", Toast.LENGTH_SHORT).show()
        } catch (e: CameraAccessException) {
            Log.e("Camera Problem", "Cannot turn on camera flashlight")
        }
    }

    private fun flashLightOff() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        try {
            assert(cameraManager != null)
            val cameraId = cameraManager!!.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, false)
            Toast.makeText(this@MainActivity, "FlashLight is OFF", Toast.LENGTH_SHORT).show()
        } catch (e: CameraAccessException) {
            Log.e("Camera Problem", "Cannot turn off camera flashlight")
        }
    }
}