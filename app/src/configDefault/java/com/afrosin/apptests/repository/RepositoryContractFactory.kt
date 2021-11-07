package com.afrosin.apptests.repository

import com.afrosin.apptests.presenter.RepositoryContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryContractFactory {

    private val baseURL = "https://api.github.com"

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createRepository() : RepositoryContract {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }
}