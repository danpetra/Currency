package com.example.currency.ui

import com.example.currency.data.entities.FinalEntity

object SortingUtils {

    fun List<FinalEntity>.sortByType(type:SortType): List<FinalEntity> {
        return when(type)
        {
            SortType.NO_SORT -> this
            SortType.INC_BY_NAME -> this.sortedBy { it.key }
            SortType.INC_BY_VALUE -> this.sortedBy { it.rate }
            SortType.DEC_BY_NAME -> this.sortedByDescending { it.key }
            SortType.DEC_BY_VALUE -> this.sortedByDescending { it.rate }
        }
    }

    fun getSortTypeByPos(i:Int): SortType{
        return when(SortType.inStringArray()[i]){
            SortType.INC_BY_NAME.description -> SortType.INC_BY_NAME
            SortType.DEC_BY_NAME.description -> SortType.DEC_BY_NAME
            SortType.INC_BY_VALUE.description -> SortType.INC_BY_VALUE
            SortType.DEC_BY_VALUE.description -> SortType.DEC_BY_VALUE
            else -> SortType.NO_SORT
        }
    }
}