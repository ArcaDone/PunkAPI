package com.arcadone.cheers.api

import com.arcadone.cheers.model.BeerItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {
    @GET("beers")
    suspend fun getBeers(
        @Query("page") page: Int = 1,
        @Query("per_page") itemsPerPage: Int = 25
    ): Response<List<BeerItem>>

    @GET("beers")
    suspend fun getBeersByBrewedDate(
        @Query("page") page: Int = 1,
        @Query("per_page") itemsPerPage: Int = 25,
        @Query("brewed_after") brewedAfter: String,
        @Query("brewed_before") brewedBefore: String
    ): Response<List<BeerItem>>
}
