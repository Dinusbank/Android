package com.dinusbank.tumbuhin.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.dinusbank.tumbuhin.databinding.FragmentHomeBinding
import com.dinusbank.tumbuhin.ui.ResultActivity
import com.dinusbank.tumbuhin.ui.ResultActivity.Companion.ACTION_PICKER
import com.dinusbank.tumbuhin.ui.ResultActivity.Companion.IMAGE_ID

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var imageUri: Uri? = null

    companion object{
        const val PERMISSION_CODE = 100
        const val PICK_IMAGE_GALLERY = 200
        const val CAPTURE_IMAGE = 300
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpload.setOnClickListener {
            if(context?.let { it1 -> ActivityCompat.checkSelfPermission(it1, Manifest.permission.READ_EXTERNAL_STORAGE) }
                == PERMISSION_DENIED){
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else{
                pickImageFromGallery()
            }
        }

        binding.btnCapture.setOnClickListener {
            if(context?.let { it1 -> ActivityCompat.checkSelfPermission(it1, Manifest.permission.CAMERA) }
                == PERMISSION_DENIED){
                val permission = arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permission, PERMISSION_CODE)
            } else{
                captureImage()
            }
        }
    }

    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_GALLERY)
    }

    private fun captureImage(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAPTURE_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_GALLERY){
            imageUri = data?.data

            val intent = Intent(activity, ResultActivity::class.java)
            intent.putExtra(IMAGE_ID, imageUri.toString())
            intent.putExtra(ACTION_PICKER, 1)

            startActivity(intent)
        } else if (resultCode == Activity.RESULT_OK && requestCode == CAPTURE_IMAGE){
            val imageBitmap = data?.extras?.get("data") as Bitmap?

            val intent = Intent(activity, ResultActivity::class.java)
            intent.putExtra(IMAGE_ID, imageBitmap)
            intent.putExtra(ACTION_PICKER, 2)

            startActivity(intent)
        }
    }

}