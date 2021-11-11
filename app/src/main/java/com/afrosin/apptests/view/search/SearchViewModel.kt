package com.afrosin.apptests.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afrosin.apptests.model.SearchResponse
import com.afrosin.apptests.presenter.RepositoryContract
import com.afrosin.apptests.presenter.SchedulerProvider
import com.afrosin.apptests.presenter.search.SearchSchedulerProvider
import com.afrosin.apptests.repository.RepositoryContractFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class SearchViewModel(
    private val repository: RepositoryContract = RepositoryContractFactory().createRepository(),
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : ViewModel() {

    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData

    fun subscribeToLiveData() = liveData

    fun searchGitHub(searchQuery: String) {
        //Dispose
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            repository.searchGithub(searchQuery)
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe { _liveData.value = ScreenState.Loading }
                .subscribeWith(object : DisposableObserver<SearchResponse>() {

                    override fun onNext(searchResponse: SearchResponse) {
                        val searchResults = searchResponse.searchResults
                        val totalCount = searchResponse.totalCount
                        if (searchResults != null && totalCount != null) {
                            _liveData.value = ScreenState.Working(searchResponse)
                        } else {
                            _liveData.value =
                                ScreenState.Error(Throwable("Search results or total count are null"))
                        }
                    }

                    override fun onError(e: Throwable) {
                        _liveData.value =
                            ScreenState.Error(
                                Throwable(
                                    e.message ?: "Response is null or unsuccessful"
                                )
                            )
                    }

                    override fun onComplete() {}
                }
                )
        )
    }
}

sealed class ScreenState {
    object Loading : ScreenState()
    data class Working(val searchResponse: SearchResponse) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}