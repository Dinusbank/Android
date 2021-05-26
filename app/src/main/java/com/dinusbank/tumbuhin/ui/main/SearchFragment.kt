package com.dinusbank.tumbuhin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinusbank.tumbuhin.adapter.LeafesAdapter
import com.dinusbank.tumbuhin.data.ResponseDataLeafes
import com.dinusbank.tumbuhin.databinding.FragmentSearchBinding
import com.dinusbank.tumbuhin.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var leafesAdapter: LeafesAdapter
    private val listLeafes = ArrayList<ResponseDataLeafes>()
    private val dataLeafes = ResponseDataLeafes()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())
            .get(SearchViewModel::class.java)

        searchViewModel.getLeafes()

        leafesAdapter = LeafesAdapter(mutableListOf<ResponseDataLeafes>() as ArrayList<ResponseDataLeafes>)

        searchViewModel.getSearchViewModel().observe(requireActivity(), {listDataLeafes ->
            if (listDataLeafes != null){
                leafesAdapter.setData(listDataLeafes)
                setAdapter()
                setProgressLoading(false)
                binding.leafesAdapter.visibility = View.VISIBLE
            }
        })

        setSearchView()
    }

    private fun setProgressLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setAdapter(){
        with(binding.leafesAdapter){
            adapter = leafesAdapter
            leafesAdapter = LeafesAdapter(listLeafes)
            layoutManager = LinearLayoutManager(context)
            leafesAdapter.notifyDataSetChanged()
        }
    }

    private fun setSearchView(){
        with(binding.searchView){
            onActionViewExpanded()
            setIconifiedByDefault(true)
            clearFocus()

            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    listLeafes.clear()
                    newText?.let { searchViewModel.getSearch(it) }
                    leafesAdapter.notifyDataSetChanged()

                    return true
                }
            })
        }
    }
}