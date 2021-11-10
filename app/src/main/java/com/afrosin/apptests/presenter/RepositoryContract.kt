package com.afrosin.apptests.presenter

import com.afrosin.apptests.model.SearchResponse
import com.afrosin.apptests.repository.RepositoryCallback
import io.reactivex.Observable

interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String
    ): Observable<SearchResponse>

}
