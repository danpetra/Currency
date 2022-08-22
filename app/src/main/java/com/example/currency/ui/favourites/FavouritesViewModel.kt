package com.example.currency.ui.favourites

import androidx.lifecycle.*
import com.example.currency.MainActivity
import com.example.currency.data.entities.FinalEntity
import com.example.currency.data.repository.FavouritesRepository
import com.example.currency.data.controller.ViewController
import com.example.currency.ui.template.ViewModelTemplate
import javax.inject.Inject

class FavouritesViewModel : ViewModelTemplate() {
    @Inject lateinit var repository : FavouritesRepository
    @Inject override lateinit var controller: ViewController
    override val rates: LiveData<List<FinalEntity>>
    override val mapOfCurLD: LiveData<Map<String, String>>

    init {
        MainActivity.appComponent.injectFavouritesViewModel(this)
        rates = repository.finalFlow.asLiveData(viewModelScope.coroutineContext)
        mapOfCurLD = controller.flowOfCur.asLiveData(viewModelScope.coroutineContext)
        setSortLiveData()
    }
}