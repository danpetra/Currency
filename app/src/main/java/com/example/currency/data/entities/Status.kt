package com.example.currency.data.entities

sealed class Status{
    object LOADING : Status()
    object SUCCESS : Status()
    class ERROR(val message: String) : Status()
}