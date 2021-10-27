package com.afrosin.apptests.view.search

import com.afrosin.apptests.model.SearchResult
import com.afrosin.apptests.view.ViewContract

internal interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
