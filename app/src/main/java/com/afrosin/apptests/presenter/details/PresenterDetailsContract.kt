package com.afrosin.apptests.presenter.details

import com.afrosin.apptests.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
    fun getCounter(): Int
}
