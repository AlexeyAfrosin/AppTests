package com.afrosin.apptests.repository

import com.afrosin.apptests.presenter.RepositoryContract

class RepositoryContractFactory {
    fun createRepository(): RepositoryContract {
        return FakeGitHubRepository()
    }
}