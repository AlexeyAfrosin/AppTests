package com.afrosin.apptests.presenter

import com.afrosin.apptests.view.ViewContract

internal interface PresenterContract {
    fun onAttach(view: ViewContract)
    fun onDetach()
}
