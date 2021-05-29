@file:Suppress("DEPRECATION")

package com.dinusbank.tumbuhin.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dinusbank.tumbuhin.data.remote.responses.ResponseDataLeafes
import com.dinusbank.tumbuhin.databinding.ActivityResultBinding
import com.dinusbank.tumbuhin.ml.Medleaf
import com.dinusbank.tumbuhin.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var searchViewModel: SearchViewModel

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
                3 -> getDetailLeafesSearch()
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
        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(SearchViewModel::class.java)

        searchViewModel.getLeafes()

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
                    binding.tvLeafesnameItalic.text = max.label
                }

                when(max?.label){
                    "Alpinia_galanga" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[13]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Amaranthus_viridis" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[1]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Andrographis_paniculata" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[19]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Artocarpus_heterophyllus" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[7]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Azadirachta_indica" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[6]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Basella_alba" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[2]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Brassica_juncea" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[20]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Carissa_carandas" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[4]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Citrus_limon" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[12]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Curcuma_zedoaria" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[11]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Ficus_auriculata" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[0]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Hibiscus_rosa-sinensis" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[5]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Jasminum_sp" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[16]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Mangifera_indica" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[15]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Mentha_sp" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[17]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Moringa_oleifera" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[10]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Nyctanthes_arbor-tristis" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[22]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Ocimum_tenuiflorum" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[18]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Piper_betle" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[21]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Plectranthus_amboinicus" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[9]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Pongamia_pinnata" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[14]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Psidium_guajava" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[8]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }

                    "Tinospora_cordifolia" -> {
                        searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                            if (listDataLeafes != null){
                                val leafes = listDataLeafes[3]

                                binding.tvLeafesname.text = leafes.name
                                binding.tvManfaat.text = leafes.benefits
                                binding.tvKomposisi.text = leafes.composition
                            }
                        })
                    }
                }

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

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(SearchViewModel::class.java)

        searchViewModel.getLeafes()

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
                binding.tvLeafesnameItalic.text = max.label
            }

            when(max?.label){
                "Alpinia_galanga" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[13]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Amaranthus_viridis" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[1]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Andrographis_paniculata" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[19]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Artocarpus_heterophyllus" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[7]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Azadirachta_indica" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[6]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Basella_alba" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[2]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Brassica_juncea" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[20]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Carissa_carandas" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[4]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Citrus_limon" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[12]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Curcuma_zedoaria" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[11]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Ficus_auriculata" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[0]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Hibiscus_rosa-sinensis" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[5]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Jasminum_sp" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[16]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Mangifera_indica" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[15]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Mentha_sp" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[17]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Moringa_oleifera" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[10]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Nyctanthes_arbor-tristis" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[22]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Ocimum_tenuiflorum" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[18]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Piper_betle" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[21]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Plectranthus_amboinicus" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[9]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Pongamia_pinnata" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[14]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Psidium_guajava" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[8]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }

                "Tinospora_cordifolia" -> {
                    searchViewModel.getSearchViewModel().observe(this, {listDataLeafes ->
                        if (listDataLeafes != null){
                            val leafes = listDataLeafes[3]

                            binding.tvLeafesname.text = leafes.name
                            binding.tvManfaat.text = leafes.benefits
                            binding.tvKomposisi.text = leafes.composition
                        }
                    })
                }
            }

            model.close()

            Glide.with(this)
                .load(imageBitmap)
                .override(312,416)
                .into(binding.ivLeafes)
        }
    }

    private fun getDetailLeafesSearch(){
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