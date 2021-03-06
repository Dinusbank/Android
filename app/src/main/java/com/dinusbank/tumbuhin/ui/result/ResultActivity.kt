@file:Suppress("DEPRECATION")
package com.dinusbank.tumbuhin.ui.result

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
import com.dinusbank.tumbuhin.databinding.ActivityResultBinding
import com.dinusbank.tumbuhin.ml.Medleaf
import com.dinusbank.tumbuhin.viewmodel.SearchViewModel
import com.dinusbank.tumbuhin.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.File
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.min

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var searchViewModel: SearchViewModel

    companion object{
        const val IMAGE_ID = "image_id"
        const val ACTION_PICKER = "action_picker"
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
            }
        }

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun setImageFromGallery(){
        val bundle: Bundle? = intent.extras

        if (bundle != null){
            val setImage = Uri.parse(bundle.getString(IMAGE_ID))
            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, setImage)
            setImageBinding(imageBitmap)
            setTensorflowModel(imageBitmap, bundle)
        }
    }

    private fun setImageFromCamera(){
        val bundle: Bundle? = intent.extras

        if (bundle != null){
            val photoPath = Uri.parse(bundle.getString(IMAGE_ID))

            val targetW = 896
            val targetH = 896

            val bmOptions = BitmapFactory.Options().apply {
                inJustDecodeBounds = true

                val photoW: Int = outWidth
                val photoH: Int = outHeight

                val scaleFactor: Int = max(1, min(photoW / targetW, photoH / targetH))

                inJustDecodeBounds = false
                inSampleSize = scaleFactor
                inPurgeable = true
            }

            BitmapFactory.decodeFile(photoPath.toString(), bmOptions)?.also { imageBitmap ->
                setImageBinding(imageBitmap)
                setTensorflowModel(imageBitmap, bundle)
            }
        }
    }

    private fun setTensorflowModel(imageBitmap: Bitmap, bundle: Bundle?){
        val model = Medleaf.newInstance(this)

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224,224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        var tImage = TensorImage(DataType.UINT8)
        tImage.load(imageBitmap)
        tImage = imageProcessor.process(tImage)

        val outputs = model.process(tImage)
        val probability = outputs.probabilityAsCategoryList

        val max = probability.maxByOrNull { it.score }

        if (max != null) {
            val percentage = (max.score / 1) * 100

            if (percentage > 30){
                (DecimalFormat("##").format(percentage) + " %").also { binding.tvAccuracy.text = it }
                binding.tvLeafesnameItalic.text = max.label
                setNormalVisibility()
            } else{
                setErrorVisibility()
            }
        }

        max?.label?.let { getData(it) }

        model.close()

        if (bundle != null) {
            val photoPath = Uri.parse(bundle.getString(IMAGE_ID))
            val imgFile = File(photoPath.toString())

            imgFile.delete()
        }
    }

    private fun setErrorVisibility(){
        binding.icErrormsg.visibility = View.VISIBLE
        binding.tvErrormsg.visibility = View.VISIBLE
        binding.ivLeafes.visibility = View.GONE
        binding.sheet.visibility = View.GONE
    }

    private fun setNormalVisibility(){
        binding.icErrormsg.visibility = View.GONE
        binding.tvErrormsg.visibility = View.GONE
        binding.ivLeafes.visibility = View.VISIBLE
        binding.sheet.visibility = View.VISIBLE
    }

    private fun showProgressLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setDataBinding(leafes: LeafesEntities){
        binding.tvLeafesname.text = leafes.name
        binding.tvManfaat.text = leafes.benefits
        binding.tvKomposisi.text = leafes.composition
    }

    private fun setImageBinding(imageBitmap: Bitmap){
        Glide.with(this)
            .load(imageBitmap)
            .override(896,896)
            .into(binding.ivLeafes)
    }

    private fun getData(max: String){
        val factory = ViewModelFactory.getInstance(this)
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        when(max){
            "Alpinia_galanga" -> {
                searchViewModel.getSearch("Lengkuas").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Amaranthus_viridis" -> {
                searchViewModel.getSearch("Bayam Hijau").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Andrographis_paniculata" -> {
                searchViewModel.getSearch("Sambiloto").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Artocarpus_heterophyllus" -> {
                searchViewModel.getSearch("Buah Sukun").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Azadirachta_indica" -> {
                searchViewModel.getSearch("Daun Mimba").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Basella_alba" -> {
                searchViewModel.getSearch("Bayam Malabar").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Brassica_juncea" -> {
                searchViewModel.getSearch("Sesawi India").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Carissa_carandas" -> {
                searchViewModel.getSearch("Buah Samarinda").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Citrus_limon" -> {
                searchViewModel.getSearch("Lemon").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Curcuma_zedoaria" -> {
                searchViewModel.getSearch("Kunyit Putih").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Ficus_auriculata" -> {
                searchViewModel.getSearch("Ara").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Hibiscus_rosa-sinensis" -> {
                searchViewModel.getSearch("Bunga Sepatu").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Jasminum_sp" -> {
                searchViewModel.getSearch("Melati").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Mangifera_indica" -> {
                searchViewModel.getSearch("Mangga").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Mentha_sp" -> {
                searchViewModel.getSearch("Mint").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Moringa_oleifera" -> {
                searchViewModel.getSearch("Kelor").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Nyctanthes_arbor-tristis" -> {
                searchViewModel.getSearch("Srigading").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Ocimum_tenuiflorum" -> {
                searchViewModel.getSearch("Ruku-ruku").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Piper_betle" -> {
                searchViewModel.getSearch("Sirih").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Plectranthus_amboinicus" -> {
                searchViewModel.getSearch("Jintan").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Pongamia_pinnata" -> {
                searchViewModel.getSearch("Malapari").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Psidium_guajava" -> {
                searchViewModel.getSearch("Jambu Biji").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }

            "Tinospora_cordifolia" -> {
                searchViewModel.getSearch("Bratawali").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]
                    leafes?.let { setDataBinding(it) }
                })
            }
        }
    }
}