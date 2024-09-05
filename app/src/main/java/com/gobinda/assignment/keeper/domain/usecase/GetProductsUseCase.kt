package com.gobinda.assignment.keeper.domain.usecase

import com.gobinda.assignment.keeper.domain.model.Section

interface GetProductsUseCase {

    suspend operator fun invoke() : List<Section>
}