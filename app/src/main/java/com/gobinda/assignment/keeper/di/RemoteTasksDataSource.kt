package com.gobinda.assignment.keeper.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import com.gobinda.assignment.keeper.data.api.RestApi
import com.gobinda.assignment.keeper.data.api.Retrofit
import com.gobinda.assignment.keeper.data.domain_impl.GetProductsUseCaseImpl
import com.gobinda.assignment.keeper.domain.usecase.GetProductsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteTasksDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalTasksDataSource


@InstallIn(SingletonComponent::class)
@Module
 class DataModule {

    @Singleton
    @Provides
    fun provideApi(): RestApi = Retrofit.api

    @Singleton
    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources = context.resources

}

@InstallIn(ViewModelComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindsGetProducts( products: GetProductsUseCaseImpl) : GetProductsUseCase

}



