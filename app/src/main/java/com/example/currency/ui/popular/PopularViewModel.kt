package com.example.currency.ui.popular

import androidx.lifecycle.*
import com.example.currency.MainActivity
import com.example.currency.data.entities.FinalEntity
import com.example.currency.data.repository.PopularRepository
import com.example.currency.data.controller.ViewController
import com.example.currency.data.entities.Status
import com.example.currency.ui.template.ViewModelTemplate
import javax.inject.Inject

class PopularViewModel : ViewModelTemplate() {

    @Inject lateinit var repository : PopularRepository
    @Inject override lateinit var controller: ViewController
    override val rates: LiveData<List<FinalEntity>>
    override val mapOfCurLD: LiveData<Map<String, String>>

    val statusLiveData: LiveData<Status>

    init {
        MainActivity.appComponent.injectPopularViewModel(this)
        rates = repository.finalFlow.asLiveData(viewModelScope.coroutineContext)
        mapOfCurLD = controller.flowOfCur.asLiveData(viewModelScope.coroutineContext)
        statusLiveData = repository.statusFlow.asLiveData(viewModelScope.coroutineContext)
        setSortLiveData()
    }
}