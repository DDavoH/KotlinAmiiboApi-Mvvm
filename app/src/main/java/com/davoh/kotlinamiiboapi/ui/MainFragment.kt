package com.davoh.kotlinamiiboapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.davoh.kotlinamiiboapi.R
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.ui.adapters.MainAdapter
import com.davoh.kotlinamiiboapi.ui.viewModel.MainViewModel
import com.davoh.kotlinamiiboapi.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnAmiiboClickListener {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        searchView()
        observers()
    }

    private fun recyclerView(){
        rv_amiibos.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun searchView(){
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.setAmiibo(query)
                }else{
                    viewModel.setAmiibo("")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observers(){
        viewModel.fetchAmiibosList.observe(viewLifecycleOwner, Observer{ result->
            when(result){
                is Resource.Loading -> {
                    progresBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progresBar.visibility = View.GONE
                    rv_amiibos.adapter = MainAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    progresBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Ocurrió un error al traer los datos ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })



    }

    override fun onAmiiboClick(amiibo: Amiibo) {
        val bundle = Bundle()
        bundle.putParcelable("amiibo", amiibo)
        findNavController().navigate(R.id.action_mainFragment_to_amiiboDetails,bundle)
    }


}