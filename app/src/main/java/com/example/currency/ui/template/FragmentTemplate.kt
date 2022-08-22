package com.example.currency.ui.template

import android.app.AlertDialog
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.ui.CurrencySpinnerPresenter.getKeysList
import com.example.currency.ui.CurrencySpinnerPresenter.toPresenterList
import com.example.currency.ui.OnUserSelectListener
import com.example.currency.ui.RatesAdapter
import com.example.currency.ui.SortType
import com.example.currency.ui.SortingUtils

abstract class FragmentTemplate: Fragment() {

    abstract val viewModel: ViewModelTemplate
    abstract val ratesRecyclerView: RecyclerView
    abstract val sortButton: ImageButton
    abstract val currenciesSpinner: Spinner

    fun setElements(){
        setRatesRecView()
        setSortButton()
        setCurrencySpinner()
    }

    private fun setRatesRecView(){
        val ratesAdapter = RatesAdapter()
        ratesAdapter.clickListener = { pos, key -> viewModel.onAddClick(pos,key) }
        viewModel.sortedRates.observe(viewLifecycleOwner) {
            ratesAdapter.setList(it)
        }
        ratesRecyclerView.adapter = ratesAdapter
        ratesRecyclerView.layoutManager = GridLayoutManager(this.context, 1)
    }

    private fun setSortButton(){
        sortButton.setOnClickListener {
            showSortDialog()
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
            currenciesSpinner.setSelection(it.getKeysList().indexOf(viewModel.currentCurrency()))
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currenciesSpinner.adapter = spinnerAdapter
        val listener = OnUserSelectListener(object : OnUserSelectListener.OnClick {
            override fun onClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.onCurrencyClick(p2)
            }
        })
        currenciesSpinner.setOnTouchListener(listener)
        currenciesSpinner.onItemSelectedListener = listener
    }

    fun setCurrencySpinnerPosition(){
        currenciesSpinner.setSelection(
            viewModel.mapOfCur.getKeysList().indexOf(viewModel.currentCurrency())
        )
    }

    private fun showSortDialog(){
        val dialog = AlertDialog.Builder(this.context)
        dialog.apply{
            setTitle("Type of sorting")
            setNegativeButton("Close"){ dialogInterface,_ ->
                dialogInterface.dismiss()
            }
            setItems(SortType.inStringArray()){ _, pos ->
                viewModel.onSortClick(SortingUtils.getSortTypeByPos(pos))
            }
            show()
        }
    }
}