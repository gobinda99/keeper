package com.gobinda.assignment.keeper.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gobinda.assignment.keeper.data.domain_impl.SecProductsPagingSource
import com.gobinda.assignment.keeper.domain.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val secProductsPagingSource: SecProductsPagingSource,
) : ViewModel() {

    private val _secProducts: MutableStateFlow<PagingData<Section>> =
        MutableStateFlow(PagingData.empty())
    var secProducts = _secProducts.asStateFlow()
        private set

    init {
        fetchSections()
    }

    fun onRetry() {
        fetchSections()
    }

    private fun fetchSections() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    10, enablePlaceholders = true
                )
            ) {
                secProductsPagingSource
            }.flow.catch {
                Timber.e(it, "error")
            }.cachedIn(viewModelScope).collect {
                    _secProducts.value = it
                }
        }
    }

}