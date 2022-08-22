package com.example.currency.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currency.databinding.FragmentSettingsBinding
import com.example.currency.ui.APP_PREFERENCES
import com.example.currency.ui.APP_PREFERENCES_BASE_CURRENCY
import com.example.currency.ui.CurrencySpinnerPresenter.getKeysList
import com.example.currency.ui.CurrencySpinnerPresenter.toPresenterList
import com.example.currency.ui.OnUserSelectListener

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SettingsViewModel
    private val currenciesSpinner
        get() = binding.spinner2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        viewModel.preferences = context?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        setCurrencySpinner()
        binding.overrideButton.setOnClickListener {
            viewModel.onOverrideClick()
        }
    }

    private fun setCurrencySpinner(){
        val arrayList: ArrayList<String> = ArrayList(viewModel.mapOfCur.toPresenterList())
        val spinnerAdapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            arrayList
        )
        viewModel.mapOfCurLD.observe(viewLifecycleOwner) {
            spinnerAdapter.clear()
            spinnerAdapter.addAll(it.toPresenterList())
            spinnerAdapter.notifyDataSetChanged()
            currenciesSpinner.setSelection(it.getKeysList().indexOf(
                viewModel.preferences?.getString(APP_PREFERENCES_BASE_CURRENCY, "EUR")
            ))
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currenciesSpinner.adapter = spinnerAdapter
        val listener = OnUserSelectListener(object : OnUserSelectListener.OnClick {
            override fun onClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.onCurrencyClick(p2)
                Log.i("Spinner","Onclick")
            }
        })
        currenciesSpinner.setOnTouchListener(listener)
        currenciesSpinner.onItemSelectedListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}