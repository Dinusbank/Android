package com.dinusbank.tumbuhin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
import com.dinusbank.tumbuhin.databinding.ItemPlantsBinding
import com.dinusbank.tumbuhin.ui.result.DetailsActivity
import com.dinusbank.tumbuhin.ui.result.DetailsActivity.Companion.LEAFES

class LeafesAdapter: PagedListAdapter<LeafesEntities, LeafesAdapter.LeafesViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LeafesEntities>() {
            override fun areItemsTheSame(oldItem: LeafesEntities, newItem: LeafesEntities): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LeafesEntities, newItem: LeafesEntities): Boolean {
                return oldItem == newItem
            }

        }
    }

    class LeafesViewHolder(private val binding: ItemPlantsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(leafesEntities: LeafesEntities){
            binding.tvLeafesname.text = leafesEntities.name
            binding.tvLeafesnameItalic.text = leafesEntities.latinName

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailsActivity::class.java)
                intent.putExtra(LEAFES, leafesEntities)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeafesViewHolder {
        val itemPlantsBinding = ItemPlantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeafesViewHolder(itemPlantsBinding)
    }

    override fun onBindViewHolder(holder: LeafesViewHolder, position: Int) {
        val leafesItem = getItem(position)
        if (leafesItem != null){
            holder.bind(leafesItem)
        }
    }
}