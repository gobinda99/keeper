package com.gobinda.assignment.keeper.data.domain_impl

import com.gobinda.assignment.keeper.data.DataSource
import com.gobinda.assignment.keeper.domain.model.Section
import com.gobinda.assignment.keeper.domain.usecase.GetProductsUseCase
import javax.inject.Inject


class GetProductsUseCaseImpl @Inject constructor(private val dataStore: DataSource) : GetProductsUseCase {

    override suspend fun invoke(): List<Section> {

        return dataStore.api.getProducts()
    }
}