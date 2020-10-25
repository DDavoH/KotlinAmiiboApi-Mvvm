package com.davoh.kotlinamiiboapi.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davoh.kotlinamiiboapi.R
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.databinding.FragmentMainBinding
import com.davoh.kotlinamiiboapi.ui.adapters.MainAdapter
import com.davoh.kotlinamiiboapi.ui.viewModel.MainViewModel
import com.davoh.kotlinamiiboapi.vo.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnAmiiboClickListener {

    private val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentMainBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view =  binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        searchView()
        swipeToRefresh()
        observers()


    }

    private fun recyclerView(){
        binding.rvAmiibos.layoutManager =  LinearLayoutManager(requireContext())
    }

    private fun searchView(){
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
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

        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                Log.d("seachview", "onClose: ")
                binding.root.hideKeyboard()
                return true
            }

            fun View.hideKeyboard() {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        })

        }

    private fun swipeToRefresh(){
        //** Set the colors of the Pull To Refresh View
        binding.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        binding.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)

        binding.itemsswipetorefresh.setOnRefreshListener {
            viewModel.setAmiibo("")
            binding.itemsswipetorefresh.isRefreshing = false
        }
    }

    private fun observers(){
        viewModel.fetchAmiibosList.observe(viewLifecycleOwner, Observer{ result->
            when(result){
                is Resource.Loading -> {
                    binding.progresBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progresBar.visibility = View.GONE
                    binding.rvAmiibos.adapter = MainAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    binding.progresBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Ocurrió un error al traer los datos ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })



    }

    override fun onAmiiboClick(amiibo: Amiibo) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAmiiboDetails(amiibo))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.toolbar_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToFavoriteFragment())
                false
            }
            else -> false
        }
    }

    

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}




