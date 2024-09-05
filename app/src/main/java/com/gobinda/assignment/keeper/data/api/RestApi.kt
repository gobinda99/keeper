package com.gobinda.assignment.keeper.data.api

import com.gobinda.assignment.keeper.domain.model.Section
import retrofit2.http.GET

interface RestApi {

    @GET("/b/5BEJ")
    suspend fun getProducts(/*@Path("period") period: Int, @Query("api-key") key : String*/): List<Section>
}