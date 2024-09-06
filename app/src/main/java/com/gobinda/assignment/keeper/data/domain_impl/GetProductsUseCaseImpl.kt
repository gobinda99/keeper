package com.gobinda.assignment.keeper.data.domain_impl

import com.gobinda.assignment.keeper.data.api.RestApi
import com.gobinda.assignment.keeper.domain.model.Section
import com.gobinda.assignment.keeper.domain.usecase.GetProductsUseCase
import javax.inject.Inject


class GetProductsUseCaseImpl @Inject constructor(private val api: RestApi) : GetProductsUseCase {

    override suspend fun invoke(): List<Section> {

        return api.getSectionsProducts()
    }
}