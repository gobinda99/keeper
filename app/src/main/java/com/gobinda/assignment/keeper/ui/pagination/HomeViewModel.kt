package com.gobinda.assignment.keeper.ui.pagination
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gobinda.assignment.keeper.data.domain_impl.ProductsPagingSource
import com.gobinda.assignment.keeper.domain.model.Section
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsPagingSource: ProductsPagingSource,
) : ViewModel() {

    private val _sections: MutableStateFlow<PagingData<Section>> =
        MutableStateFlow(PagingData.empty())
    var sections = _sections.asStateFlow()
        private set

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    10, enablePlaceholders = true
                )
            ) {
                productsPagingSource
            }.flow.cachedIn(viewModelScope).collect {
                _sections.value = it
            }
        }
    }

}