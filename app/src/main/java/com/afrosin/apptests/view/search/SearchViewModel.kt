package com.afrosin.apptests.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrosin.apptests.model.SearchResponse
import com.afrosin.apptests.presenter.RepositoryContract
import com.afrosin.apptests.presenter.SchedulerProvider
import com.afrosin.apptests.presenter.search.SearchSchedulerProvider
import com.afrosin.apptests.repository.RepositoryContractFactory
import kotlinx.coroutines.*

class SearchViewModel(
    private val repository: RepositoryContract = RepositoryContractFactory().createRepository(),
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : ViewModel() {

    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    private fun handleError(e: Throwable) {
        _liveData.value = ScreenState.Error(Throwable(e))
        _liveData.value =
            ScreenState.Error(
                Throwable(
                    e.message ?: "Response is null or unsuccessful"
                )
            )

    }


    fun subscribeToLiveData() = liveData

    fun searchGitHub(searchQuery: String) {
        _liveData.value = ScreenState.Loading
        viewModelCoroutineScope.launch {
            val searchResponse = repository.searchGithubAsync(searchQuery)
            val searchResults = searchResponse.searchResults
            val totalCount = searchResponse.totalCount
            if (searchResults != null && totalCount != null) {
                _liveData.value = ScreenState.Working(searchResponse)
            } else {
                _liveData.value =
                    ScreenState.Error(Throwable("Search results or total count are null"))
            }
        }

    }
}

sealed class ScreenState {
    object Loading : ScreenState()
    data class Working(val searchResponse: SearchResponse) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}