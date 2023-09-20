package com.example.kamepa

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var camera: Camera
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var vFilename: String = ""

    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var surfaceView: SurfaceView
    private lateinit var captureButton: Button
    private lateinit var myImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        captureButton = findViewById(R.id.PictureButton)
        myImageView = findViewById(R.id.myImageView)




        captureButton.setOnClickListener {
            openCamera()
        }

    }

    private fun checkCameraPermission(): Boolean {
        return if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            true }
        else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 1)
            false
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")

        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // set filename
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        vFilename = "FOTO_" + timeStamp + ".jpg"
        // set direcory folder
        val file = File("/sdcard/niabsen/", vFilename);
        val image_uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            //File object of camera image
            val file = File("/sdcard/niabsen/", vFilename);
            //longToast(file.toString())

            //Uri of camera image
            val uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
            Log.i("uri","uri")
            myImageView.setImageURI(uri)
        }
    }
}