package com.gobinda.assignment.keeper.data

import android.content.Context
import com.gobinda.assignment.keeper.data.api.RestApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DataSource class to access database, and api.
 * This object is created  through dagger (DI) and it is used
 * in dependency injection through Object Graph.
 * The scope is Singleton it instance life will be entire application
 */
@Singleton
class DataSource @Inject constructor( val api :RestApi) {
    //
}