package com.dinusbank.tumbuhin.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.dinusbank.tumbuhin.data.ResponseDataLeafes
import com.dinusbank.tumbuhin.databinding.ActivityResultBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

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

        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val bundle: Bundle? = intent.extras

        if (bundle != null){
            showProgressLoading(false)
            binding.ivLeafes.visibility = View.VISIBLE

            when (bundle.getInt(ACTION_PICKER)) {
                1 -> setImageFromGallery()
                2 -> setImageFromCamera()
                3 -> getDetailLeafes()
            }
        }

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun showProgressLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setImageFromGallery(){
        val bundle: Bundle? = intent.extras

        if (bundle != null){
            val setImage: Uri = Uri.parse(bundle.getString(IMAGE_ID))

            Glide.with(this)
                .load(setImage)
                .override(312,416)
                .into(binding.ivLeafes)
        }
    }

    private fun setImageFromCamera(){
        val imageBitmap: Bitmap? = intent.getParcelableExtra(IMAGE_ID)

        if (imageBitmap != null){
            Glide.with(this)
                .load(imageBitmap)
                .override(312,416)
                .into(binding.ivLeafes)
        }
    }

    private fun getDetailLeafes(){
        val dataLeafes = intent.getParcelableExtra<ResponseDataLeafes>(LEAFES) as ResponseDataLeafes

        binding.tvLeafesname.text = dataLeafes.name
        binding.tvLeafesnameItalic.text = dataLeafes.latinName
        binding.tvManfaat.text = dataLeafes.benefits
        binding.tvKomposisi.text = dataLeafes.composition
        Glide.with(this)
            .load(dataLeafes.imageLeafes)
            .override(600,300)
            .into(binding.ivLeafes)
    }
}