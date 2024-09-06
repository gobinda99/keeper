package com.gobinda.assignment.keeper.data.domain_impl

import com.gobinda.assignment.keeper.data.api.RestApi
import com.gobinda.assignment.keeper.domain.model.Section
import com.gobinda.assignment.keeper.domain.usecase.GetSecProductsUseCase
import javax.inject.Inject


class GetSecProductsUseCaseImpl @Inject constructor(private val api: RestApi) : GetSecProductsUseCase {

    override suspend fun invoke(): List<Section> {

        return api.getSectionsProducts()
    }
}