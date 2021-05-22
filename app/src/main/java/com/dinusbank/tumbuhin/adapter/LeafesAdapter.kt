package com.dinusbank.tumbuhin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinusbank.tumbuhin.R
import com.dinusbank.tumbuhin.data.ResponseDataLeafes
import com.dinusbank.tumbuhin.databinding.ItemPlantsBinding
import com.dinusbank.tumbuhin.ui.ResultActivity
import com.dinusbank.tumbuhin.ui.ResultActivity.Companion.ACTION_PICKER
import com.dinusbank.tumbuhin.ui.ResultActivity.Companion.LEAFES

class LeafesAdapter(private val listDataLeafes: ArrayList<ResponseDataLeafes>): RecyclerView.Adapter<LeafesAdapter.LeafesViewHolder>() {

    fun setData(responseDataLeafes: ArrayList<ResponseDataLeafes>){
        listDataLeafes.addAll(responseDataLeafes)
        notifyDataSetChanged()
    }

    inner class LeafesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPlantsBinding.bind(itemView)

        fun bind(responseDataLeafes: ResponseDataLeafes){
            binding.tvLeafesname.text = responseDataLeafes.name
            binding.tvLeafesnameItalic.text = responseDataLeafes.latinName

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ResultActivity::class.java)
                intent.putExtra(ACTION_PICKER, 3)
                intent.putExtra(LEAFES, responseDataLeafes)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeafesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_plants, parent, false)
        return LeafesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeafesViewHolder, position: Int) {
        holder.bind(listDataLeafes[position])
    }

    override fun getItemCount(): Int {
        return listDataLeafes.size
    }

}