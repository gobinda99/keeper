package com.gobinda.assignment.keeper.data.domain_impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gobinda.assignment.keeper.data.DataSource

import com.gobinda.assignment.keeper.domain.model.Section
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(
    private val repository: DataSource
) : PagingSource<Int, Section>() {

    override fun getRefreshKey(state: PagingState<Int, Section>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Section> {
        val page = params.key ?: 1
     /* val response = repository.getProducts(page, params.loadSize)*/
        var response = emptyList<Section>()
        if (page == 1) {
             response = repository.api.getProducts()
        }
        return try {

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(
                e
            )
        }
    }
}