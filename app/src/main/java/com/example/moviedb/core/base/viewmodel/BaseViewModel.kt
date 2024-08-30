package com.example.moviedb.core.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    // LiveData for error messages
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // LiveData for loading state
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // Coroutine exception handler to handle errors globally
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _error.postValue(throwable.localizedMessage)
        _loading.postValue(false)
    }

    /**
     * Collect a Flow and handle loading and errors.
     */
    protected fun <T> collectFlow(
        flow: Flow<T>,
        onCollect: (T) -> Unit,
    ) {
        viewModelScope.launch(coroutineExceptionHandler) {
            flow
                .catch { throwable ->
                    _error.postValue(throwable.localizedMessage)
                }
                .collect { data ->
                    onCollect(data)
                }
        }
    }

    fun startLoading() {
        _loading.value = true
    }
    fun stopLoading() {
        _loading.value = false
    }
}