package com.example.pixabay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//https://pixabay.com/api/
interface PixaApi {
    @GET("api/")
    fun getImages(
        @Query ("key") key: String="40944619-89ece41f2a88bd64626ce37ec",
        @Query("q") keyWord: String,
        @Query ("per_page") perPage: Int=3,
        @Query ("page") page: Int=1,
        ): Call<PixaModel>
}