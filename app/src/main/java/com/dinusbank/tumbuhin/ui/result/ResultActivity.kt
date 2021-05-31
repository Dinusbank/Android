@file:Suppress("DEPRECATION")

package com.dinusbank.tumbuhin.ui.result

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dinusbank.tumbuhin.databinding.ActivityResultBinding
import com.dinusbank.tumbuhin.ml.Medleaf
import com.dinusbank.tumbuhin.viewmodel.SearchViewModel
import com.dinusbank.tumbuhin.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.text.DecimalFormat

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
            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, setImage)

            if (imageBitmap != null){
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

                    (DecimalFormat("##.#").format(percentage) + " %").also { binding.tvAccuracy.text = it }
                    binding.tvLeafesnameItalic.text = max.label

//                    if (percentage > 30){
//                        (DecimalFormat("##.#").format(percentage) + " %").also { binding.tvAccuracy.text = it }
//                        binding.tvLeafesnameItalic.text = max.label
//                    } else{
//                        //SET ERROR MESSAGE
//                    }
                }

                max?.label?.let { getData(it) }

                model.close()

                Glide.with(this)
                    .load(imageBitmap)
                    .override(312,416)
                    .into(binding.ivLeafes)
            }
        }
    }

    private fun setImageFromCamera(){
        val imageBitmap: Bitmap? = intent.getParcelableExtra(IMAGE_ID)

        if (imageBitmap != null){
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

                (DecimalFormat("##.#").format(percentage) + " %").also { binding.tvAccuracy.text = it }
                binding.tvLeafesnameItalic.text = max.label

//                if (percentage > 30){
//                    (DecimalFormat("##.#").format(percentage) + " %").also { binding.tvAccuracy.text = it }
//                    binding.tvLeafesnameItalic.text = max.label
//                } else{
//                    //SET ERROR MESSAGE
//                }
            }

            max?.label?.let { getData(it) }

            model.close()

            Glide.with(this)
                .load(imageBitmap)
                .override(312,416)
                .into(binding.ivLeafes)
        }
    }

    private fun getData(max: String){
        val factory = ViewModelFactory.getInstance(this)
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        when(max){
            "Alpinia_galanga" -> {
                searchViewModel.getSearch("Lengkuas").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Amaranthus_viridis" -> {
                searchViewModel.getSearch("Bayam Hijau").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Andrographis_paniculata" -> {
                searchViewModel.getSearch("Sambiloto").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Artocarpus_heterophyllus" -> {
                searchViewModel.getSearch("Buah Sukun").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Azadirachta_indica" -> {
                searchViewModel.getSearch("Daun Mimba").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Basella_alba" -> {
                searchViewModel.getSearch("Bayam Malabar").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Brassica_juncea" -> {
                searchViewModel.getSearch("Sesawi India").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Carissa_carandas" -> {
                searchViewModel.getSearch("Buah Samarinda").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Citrus_limon" -> {
                searchViewModel.getSearch("Lemon").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Curcuma_zedoaria" -> {
                searchViewModel.getSearch("Kunyit Putih").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Ficus_auriculata" -> {
                searchViewModel.getSearch("Ara").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Hibiscus_rosa-sinensis" -> {
                searchViewModel.getSearch("Bunga Sepatu").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Jasminum_sp" -> {
                searchViewModel.getSearch("Melati").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Mangifera_indica" -> {
                searchViewModel.getSearch("Mangga").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Mentha_sp" -> {
                searchViewModel.getSearch("Mint").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Moringa_oleifera" -> {
                searchViewModel.getSearch("Kelor").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Nyctanthes_arbor-tristis" -> {
                searchViewModel.getSearch("Srigading").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Ocimum_tenuiflorum" -> {
                searchViewModel.getSearch("Ruku-ruku").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Piper_betle" -> {
                searchViewModel.getSearch("Sirih").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Plectranthus_amboinicus" -> {
                searchViewModel.getSearch("Jintan").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Pongamia_pinnata" -> {
                searchViewModel.getSearch("Malapari").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Psidium_guajava" -> {
                searchViewModel.getSearch("Jambu Biji").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }

            "Tinospora_cordifolia" -> {
                searchViewModel.getSearch("Bratawali").observe(this, {listDataLeafes ->
                    val leafes = listDataLeafes[0]

                    if (leafes != null){
                        binding.tvLeafesname.text = leafes.name
                        binding.tvManfaat.text = leafes.benefits
                        binding.tvKomposisi.text = leafes.composition
                    }
                })
            }
        }
    }
}