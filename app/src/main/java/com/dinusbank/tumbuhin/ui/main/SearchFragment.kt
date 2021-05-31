package com.dinusbank.tumbuhin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinusbank.tumbuhin.adapter.LeafesAdapter
import com.dinusbank.tumbuhin.databinding.FragmentSearchBinding
import com.dinusbank.tumbuhin.viewmodel.SearchViewModel
import com.dinusbank.tumbuhin.viewmodel.ViewModelFactory
import com.dinusbank.tumbuhin.vo.Status

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = context?.let { ViewModelFactory.getInstance(it) }
        searchViewModel = factory?.let { ViewModelProvider(this, it)}!![SearchViewModel::class.java]

        val leafesAdapter = LeafesAdapter()

        activity?.let {
            searchViewModel.getLeafes().observe(it, { leafes ->
                if (leafes != null){
                    when(leafes.status){
                        Status.LOADING -> setProgressLoading(true)
                        Status.SUCCESS -> {
                            setProgressLoading(false)

                            leafesAdapter.submitList(leafes.data)

                            with(binding.leafesAdapter){
                                adapter = leafesAdapter
                                visibility = View.VISIBLE
                                this.layoutManager = LinearLayoutManager(context)
                                leafesAdapter.notifyDataSetChanged()
                            }
                        }
                        Status.ERROR -> {
                            setProgressLoading(false)
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(binding.searchView){
                onActionViewExpanded()
                setIconifiedByDefault(true)
                clearFocus()

                setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String): Boolean {

                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {

                        val searchQuery = "%$query%"

                        searchViewModel.getSearch(searchQuery).observe(it, { leafes ->
                            leafesAdapter.submitList(leafes)

                            if (leafes.isEmpty()){
                                binding.leafesAdapter.visibility = View.GONE
                                binding.imageView3.visibility = View.VISIBLE
                                binding.textNull.visibility = View.VISIBLE
                            } else{
                                binding.leafesAdapter.visibility = View.VISIBLE
                                binding.imageView3.visibility = View.GONE
                                binding.textNull.visibility = View.GONE
                            }
                        })

                        return true
                    }
                })
            }
        }
    }

    private fun setProgressLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }
}