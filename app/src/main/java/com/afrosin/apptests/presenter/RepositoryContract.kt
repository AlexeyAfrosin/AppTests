package com.afrosin.apptests.presenter

import com.afrosin.apptests.repository.RepositoryCallback

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}
