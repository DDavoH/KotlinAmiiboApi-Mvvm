package com.davoh.kotlinamiiboapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.davoh.kotlinamiiboapi.R
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.database.model.AmiiboEntity
import com.davoh.kotlinamiiboapi.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_amiibo_details.*

@AndroidEntryPoint
class AmiiboDetails : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var amiibo:Amiibo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let{
            amiibo=it.getParcelable("amiibo")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
             return inflater.inflate(R.layout.fragment_amiibo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(amiibo.image).fitCenter().into(imageView)
        txtName.text = amiibo.name
        txtAmiiboSeries.text = amiibo.amiiboSeries

        floatingActionButton.setOnClickListener{
            val amiibo:AmiiboEntity = AmiiboEntity(amiibo.head + amiibo.tail, amiibo.head, amiibo.tail,
                amiibo.amiiboSeries, amiibo.character, amiibo.gameSeries,
                amiibo.image, amiibo.name, amiibo.type)
            Toast.makeText(requireContext(),"Amiibo was added to favorite", Toast.LENGTH_SHORT).show()
            viewModel.saveAmiibo(amiibo)
        }
    }



}