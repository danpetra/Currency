package com.example.currency.ui.popular

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.data.entities.Status
import com.example.currency.databinding.FragmentPopularBinding
import com.example.currency.ui.template.FragmentTemplate

class PopularFragment : FragmentTemplate() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    override lateinit var viewModel: PopularViewModel

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
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        super.setElements()
        viewModel.statusLiveData.observe(viewLifecycleOwner){
            when (it.javaClass) {
                Status.LOADING::class.java -> binding.errorText.setText(R.string.loading)
                Status.SUCCESS::class.java -> binding.errorText.visibility = View.INVISIBLE
                Status.ERROR::class.java ->
                    binding.errorText.text = getString(
                        R.string.error,
                        (it as Status.ERROR).message
                    )
            }
        }

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