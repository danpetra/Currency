package com.example.currency.ui.template

import androidx.lifecycle.*
import com.example.currency.data.controller.ViewController
import com.example.currency.data.entities.FinalEntity
import com.example.currency.ui.CurrencySpinnerPresenter.getKeysList
import com.example.currency.ui.SortType
import com.example.currency.ui.SortingUtils.sortByType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ViewModelTemplate: ViewModel() {

    abstract val controller: ViewController
    abstract val rates: LiveData<List<FinalEntity>>
    private val sortTypeLiveData = MutableLiveData(SortType.NO_SORT)
    val sortedRates = MediatorLiveData<List<FinalEntity>>()

    abstract val mapOfCurLD: LiveData<Map<String,String>>
    val mapOfCur
        get() = mapOfCurLD.value?: mapOf()

    fun currentCurrency(): String = controller.currentCurrency

    fun setSortLiveData(){
        sortedRates.addSource(rates) {
            sortedRates.value = it.sortByType(sortTypeLiveData.value ?: SortType.NO_SORT)
        }
        sortedRates.addSource(sortTypeLiveData) {
            sortedRates.value = rates.value?.sortByType(it)
        }
    }

    fun onAddClick(pos: Int, key: String){
        viewModelScope.launch(Dispatchers.IO) {
            controller.onFavouriteChanged(key)
        }
    }

    fun onSortClick(type: SortType){
        sortTypeLiveData.value = type
    }

    fun onCurrencyClick(position: Int){
        if (controller.currentCurrency!=mapOfCur.getKeysList()[position]){
            controller.currentCurrency = mapOfCur.getKeysList()[position]
            viewModelScope.launch(Dispatchers.IO) {
                controller.onBaseChanged(controller.currentCurrency)
            }
        }
    }
}