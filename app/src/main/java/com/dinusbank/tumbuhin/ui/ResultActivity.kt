package com.dinusbank.tumbuhin.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dinusbank.tumbuhin.data.ResponseDataLeafes
import com.dinusbank.tumbuhin.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    companion object{
        const val IMAGE_ID = "image_id"
        const val ACTION_PICKER = "action_picker"
        const val LEAFES = "leafes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras

        val numberAction = bundle?.getInt(ACTION_PICKER)

        if (numberAction == 1){
            setImageFromGallery()
        } else if (numberAction == 2){
            setImageFromCamera()
        } else{
            getDetailLeafes()
        }

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun setImageFromGallery(){
        val bundle: Bundle? = intent.extras

        if (bundle != null){
            val setImage: Uri = Uri.parse(bundle.getString(IMAGE_ID))

            Glide.with(this)
                .load(setImage)
                .override(250,250)
                .into(binding.ivLeafes)
        }
    }

    private fun setImageFromCamera(){
        val imageBitmap: Bitmap? = intent.getParcelableExtra(IMAGE_ID)

        Glide.with(this)
            .load(imageBitmap)
            .override(250,250)
            .into(binding.ivLeafes)
    }

    private fun getDetailLeafes(){
        val dataLeafes = intent.getParcelableExtra<ResponseDataLeafes>(LEAFES) as ResponseDataLeafes
        binding.tvLeafesname.text = dataLeafes.name
        binding.tvLeafesnameItalic.text = dataLeafes.latinName
        binding.tvManfaat.text = dataLeafes.benefits
        binding.tvKomposisi.text = dataLeafes.composition
    }
}