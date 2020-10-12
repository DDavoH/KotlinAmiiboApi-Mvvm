package com.davoh.kotlinamiiboapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.davoh.kotlinamiiboapi.R
import com.davoh.kotlinamiiboapi.database.model.Amiibo
import com.davoh.kotlinamiiboapi.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

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


}