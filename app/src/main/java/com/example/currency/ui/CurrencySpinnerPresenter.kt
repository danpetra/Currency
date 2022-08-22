package com.example.currency.ui

import java.lang.StringBuilder

object CurrencySpinnerPresenter {
    fun Map<String,String>.toPresenterList():List<String> = this.map {
        StringBuilder(it.key).append(" (").append(it.value).append(")").toString()
    }
    fun Map<String,String>.getKeysList() = this.keys.toList()
}