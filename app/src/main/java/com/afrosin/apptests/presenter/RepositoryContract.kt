package com.afrosin.apptests.presenter

import com.afrosin.apptests.repository.RepositoryCallback

interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}
