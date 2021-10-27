package com.afrosin.apptests.presenter.search

import com.afrosin.apptests.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
    //onAttach
    //onDetach
}
