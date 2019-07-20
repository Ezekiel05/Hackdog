package com.hackdog.hackathon.models

import com.google.gson.annotations.SerializedName

class ProductsResponse {
    @SerializedName("Id")
    var id: Int? = 0
    @SerializedName("Barcode")
    var barcode: String? = null
    @SerializedName("Name")
    var name: String? = null
    @SerializedName("Price")
    var price: Double? = null
    @SerializedName("ExpirationDate")
    var expirationDate: String? = null
    @SerializedName("Stocks")
    var stocks: Double? = null
    @SerializedName("Isle")
    var isle: IsleResponse? = null
}

class IsleResponse{
    @SerializedName("Id")
    var id: Int? = 0
    @SerializedName("Name")
    var name: String? = null
    @SerializedName("SuperMarket")
    var superMarket: SupermarketResponse? = null
}

class SupermarketResponse{
    @SerializedName("Id")
    var id: Int? = 0
    @SerializedName("Name")
    var name: String? = null
}