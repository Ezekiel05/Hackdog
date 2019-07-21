package com.hackdog.hackathon.models

import retrofit2.Call
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import retrofit2.http.*


interface ProductsService {
    @GET("GetProductsByBarcode?")
    fun getProductsByBarcode(@Query("barcode") barcode: String)
            : Call<ProductsResponse>

    @GET("ShowCartItemsByUser?")
    fun showCartItemsByUser(@Query("username") username: String)
            : Call<ProductsResponse>

    @GET("GetProductsByIsleId?")
    fun getProductsByIsleId(@Query("Id") id: Int)
            : Call<List<ProductsResponse>>

    @GET("GetIslesBySupermarketId?")
    fun getIslesBySupermarketId(@Query("Id") id: String)
            : Call<ProductsResponse>

    @GET("GetAllProducts")
    fun getAllProducts()
            : Call<List<ProductsResponse>>


    @POST("AddToCart?")
    @FormUrlEncoded
    fun addToCart(@Field("username") username: String,
                  @Field("productId") prodId: Int,
                  @Field("count") count: Int,
                  @Field("IsWishListItem") wishList: Boolean)
            : Call<ProductsResponse>
}