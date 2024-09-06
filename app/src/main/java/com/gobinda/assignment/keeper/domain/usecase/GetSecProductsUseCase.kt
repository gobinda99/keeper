package com.gobinda.assignment.keeper.domain.usecase

import com.gobinda.assignment.keeper.domain.model.Section

interface GetSecProductsUseCase {

    suspend operator fun invoke() : List<Section>
}