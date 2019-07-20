package com.hackdog.hackathon.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("GetProductsByBarcode?")
    fun getProductsByBarcode(@Query("barcode") barcode: String)
            : Call<ProductsResponse>
}