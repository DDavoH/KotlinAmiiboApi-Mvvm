package com.davoh.kotlinamiiboapi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.davoh.kotlinamiiboapi.R
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.databinding.FragmentAmiiboDetailsBinding
import com.davoh.kotlinamiiboapi.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AmiiboDetails : Fragment() {
    private val TAG = "AmiiboDetails"
    private val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentAmiiboDetailsBinding?=null
    private val binding get() = _binding!!

    private lateinit var amiibo:Amiibo

    private var isAmiiboFavorited: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let{
            AmiiboDetailsArgs.fromBundle(it).also { args->
                amiibo = args.amiibo
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAmiiboDetailsBinding.inflate(inflater, container, false)
        val view =  binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(amiibo.image).fitCenter().into(binding.imageView)
        binding.txtName.text = amiibo.name
        binding.txtAmiiboSeries.text = amiibo.amiiboSeries

        binding.floatingActionButton.setOnClickListener{
            val amiibo = Amiibo( amiibo.head, amiibo.tail,
                amiibo.amiiboSeries, amiibo.character, amiibo.gameSeries,
                amiibo.image, amiibo.name, amiibo.type)
            Toast.makeText(requireContext(),"Amiibo was added to favorite", Toast.LENGTH_SHORT).show()
            viewModel.saveOrDeleteAmiibo(amiibo)
            findNavController().navigateUp()
        }

        viewLifecycleOwner.lifecycleScope.launch{
            isAmiiboFavorited = viewModel.isAmiiboFavorite(amiibo)
            updateButtonIcon()
        }

    }

    private fun updateButtonIcon(){
        val isAmiiboFavorited = isAmiiboFavorited ?: return
        Log.d(TAG, "updateButtonIcon: ")
        when {
            isAmiiboFavorited -> binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_delete_24)
            else -> binding.floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}