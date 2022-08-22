package com.example.currency.ui

enum class SortType(val description: String) {
    NO_SORT ("No sorting"),
    INC_BY_NAME ("By name"),
    DEC_BY_NAME ("By name (dec)"),
    INC_BY_VALUE ("By value"),
    DEC_BY_VALUE ("By value (dec)");

    companion object {
        fun inStringArray() = values().map{ it.description }.toTypedArray()
    }
}