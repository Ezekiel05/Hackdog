package com.hackdog.hackathon.models


data class Products (
    var id: Int? = 0,
    var barcode: String? = null,
    var name: String? = null,
    var price: Double? = null,
    var expirationDate: String? = null,
    var stocks: Double? = null,
    var isle: Isle? = null,
    var quantity: Int? = null,
    var checked: Boolean? = false,
    var totalPrice: Double? = null
)
{
    constructor() : this(0,"","", 0.0, "", 0.0, Isle(0,"", Supermarket(0, "")), 0, false, 0.0)
}

data class Isle(
    var id: Int? = 0,
    var name: String? = null,
    var superMarket: Supermarket? = null
){
    constructor() : this(0, "", null)
}

class Supermarket(
    var id: Int? = 0,
    var name: String? = null
){
    constructor(): this(0,"")
}