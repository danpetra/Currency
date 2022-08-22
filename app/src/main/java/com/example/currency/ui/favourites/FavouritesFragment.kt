package com.example.currency.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.databinding.FragmentFavouritesBinding
import com.example.currency.ui.template.FragmentTemplate
import com.example.currency.ui.template.ViewModelTemplate

class FavouritesFragment : FragmentTemplate() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    override lateinit var viewModel: ViewModelTemplate

    override val ratesRecyclerView: RecyclerView
        get() = binding.currencyRecView
    override val sortButton: ImageButton
        get() = binding.button
    override val currenciesSpinner: Spinner
        get() = binding.spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
        super.setElements()
    }

    override fun onResume() {
        super.onResume()
        super.setCurrencySpinnerPosition()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}