package com.davoh.kotlinamiiboapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.databinding.FragmentFavoriteBinding
import com.davoh.kotlinamiiboapi.ui.adapters.FavoriteAdapter
import com.davoh.kotlinamiiboapi.ui.viewModel.MainViewModel
import com.davoh.kotlinamiiboapi.vo.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteAdapter.OnAmiiboClickListener {

    private val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentFavoriteBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view =  binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        observers()
    }

    private fun recyclerView(){
        binding.rvAmiibos.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observers(){
        viewModel.getFavoritesAmiibos().observe(viewLifecycleOwner, Observer{ result->
            when(result){
                is Resource.Loading -> {
                    binding.progresBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progresBar.visibility = View.GONE
                    binding.rvAmiibos.adapter = FavoriteAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    binding.progresBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Ocurrió un error al traer los datos ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    override fun onAmiiboClick(amiibo: Amiibo) {
       findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToAmiiboDetails(amiibo))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}