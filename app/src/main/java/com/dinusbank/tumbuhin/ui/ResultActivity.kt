package com.dinusbank.tumbuhin.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.dinusbank.tumbuhin.data.ResponseDataLeafes
import com.dinusbank.tumbuhin.databinding.ActivityResultBinding
import com.dinusbank.tumbuhin.ml.Medleaf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

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
            val labels = application.assets.open("label.txt").bufferedReader().use {
                it.readText()
            }.split("\n")

            val model = Medleaf.newInstance(this)

            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(168,168, ResizeOp.ResizeMethod.BILINEAR))
                .add(NormalizeOp(127.5f, 127.5f))
                .build()

            var tImage = TensorImage(DataType.FLOAT32)
            tImage.load(imageBitmap)
            tImage = imageProcessor.process(tImage)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 168, 168, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tImage.buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val max = getMax(outputFeature0.floatArray)

            binding.tvLeafesnameItalic.text = labels[max]

            model.close()

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

    private fun getMax(arr:FloatArray) : Int{
        var ind = 0
        var min = 0.0f

        for(i in 0..23)
        {
            if(arr[i] > min)
            {
                min = arr[i]
                ind = i
            }
        }
        return ind
    }
}