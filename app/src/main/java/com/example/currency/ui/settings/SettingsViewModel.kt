package com.example.currency.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.currency.MainActivity
import com.example.currency.data.controller.ViewController
import com.example.currency.ui.APP_PREFERENCES_BASE_CURRENCY
import com.example.currency.ui.APP_PREFERENCES_OVERRIDE_CURRENCIES
import com.example.currency.ui.CurrencySpinnerPresenter.getKeysList
import javax.inject.Inject

class SettingsViewModel : ViewModel() {

    @Inject lateinit var controller: ViewController
    val mapOfCurLD: LiveData<Map<String,String>>
    val mapOfCur
        get() = mapOfCurLD.value?: mapOf()

    var preferences: SharedPreferences? = null

    init {
        MainActivity.appComponent.injectSettingsViewModel(this)
        mapOfCurLD = controller.flowOfCur.asLiveData(viewModelScope.coroutineContext)
    }

    fun onCurrencyClick(pos: Int){
        preferences
            ?.edit()
            ?.putString(
                APP_PREFERENCES_BASE_CURRENCY,
                mapOfCur.getKeysList()[pos]
            )?.apply()
    }

    fun onOverrideClick(){
        preferences
            ?.edit()
            ?.putBoolean(
                APP_PREFERENCES_OVERRIDE_CURRENCIES,
                true
            )?.apply()
    }
}