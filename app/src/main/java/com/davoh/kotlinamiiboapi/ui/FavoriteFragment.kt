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
import com.davoh.kotlinamiiboapi.R
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.ui.adapters.FavoriteAdapter
import com.davoh.kotlinamiiboapi.ui.adapters.MainAdapter
import com.davoh.kotlinamiiboapi.ui.viewModel.MainViewModel
import com.davoh.kotlinamiiboapi.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteAdapter.OnAmiiboClickListener {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        observers()
    }

    private fun recyclerView(){
        rv_amiibos.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observers(){
        viewModel.getFavoritesAmiibos().observe(viewLifecycleOwner, Observer{ result->
            when(result){
                is Resource.Loading -> {
                    progresBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progresBar.visibility = View.GONE
                    rv_amiibos.adapter = FavoriteAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    progresBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Ocurrió un error al traer los datos ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    override fun onAmiiboClick(amiibo: AmiiboEntity) {
        val bundle = Bundle()
        findNavController().navigate(R.id.action_favoriteFragment_to_amiiboDetails,bundle)
    }

}