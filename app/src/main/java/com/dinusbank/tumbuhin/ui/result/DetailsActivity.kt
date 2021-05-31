@file:Suppress("DEPRECATION")

package com.dinusbank.tumbuhin.ui.result

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
import com.dinusbank.tumbuhin.databinding.ActivityDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    companion object{
        const val LEAFES = "leafes"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }

        getDetailLeafesSearch()
    }

    private fun showProgressLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getDetailLeafesSearch(){
        val dataLeafes = intent.getParcelableExtra<LeafesEntities>(LEAFES) as LeafesEntities

        binding.tvLeafesname.text = dataLeafes.name
        binding.tvLeafesnameItalic.text = dataLeafes.latinName
        binding.tvManfaat.text = dataLeafes.benefits
        binding.tvKomposisi.text = dataLeafes.composition

        if (dataLeafes.imageLeafes != null){
            Handler().postDelayed({ showProgressLoading(false) }, 1000)

            binding.ivLeafes.visibility = View.VISIBLE

            Glide.with(this)
                .load(dataLeafes.imageLeafes)
                .override(800,600)
                .into(binding.ivLeafes)
        }
    }
}