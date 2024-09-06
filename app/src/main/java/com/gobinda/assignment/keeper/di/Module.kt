package com.gobinda.assignment.keeper.di

import com.gobinda.assignment.keeper.data.api.RestApi
import com.gobinda.assignment.keeper.data.api.Retrofit
import com.gobinda.assignment.keeper.data.domain_impl.GetProductsUseCaseImpl
import com.gobinda.assignment.keeper.domain.usecase.GetProductsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideApi(): RestApi = Retrofit.api

}

@InstallIn(ViewModelComponent::class)
@Module
abstract class DataModuleViewModelComponent {

    @Binds
    abstract fun bindsGetProducts(products: GetProductsUseCaseImpl): GetProductsUseCase

}



