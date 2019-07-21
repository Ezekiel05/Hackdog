package com.hackdog.hackathon.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hackdog.hackathon.models.Products
import com.hackdog.hackathon.models.ProductsResponse
import com.hackdog.hackathon.repositories.ProductsRepository

class ShoppingListViewModel: ViewModel() {

    private val productsRepository = ProductsRepository()

    private val myCart = MutableLiveData<List<Products>>()
    private val cartList = mutableListOf<Products>()
    private var products = MutableLiveData<List<ProductsResponse>>()




    fun getProductByBarcode(code: String): LiveData<ProductsResponse>{
        return productsRepository.getCurrentData(code)
    }

    fun addToCart(prod: Products){

        val x = arrayListOf<Products>()
        cartList.forEach {
            if(it.barcode == prod.barcode){
                it.quantity = it.quantity!!.plus(1)
                it.totalPrice = it.quantity!! * it.price!!
                Log.d("ga", "update")
            }
            else{
                cartList.add(prod)
            }
        }

        cartList.add(prod)
        myCart.value = cartList
//        productsRepository.addToCart(prod)
    }

    fun getCartList() : LiveData<List<Products>> {
        return myCart
    }

    fun clearCart(){
        cartList.clear()
    }

    fun getAllProducts(): LiveData<List<ProductsResponse>>{
        return productsRepository.getAllProducts()
    }

//    fun setProductsByIsleID(id: Int){
//
//        products =  productsRepository.getProductsByIsleID(id)
//    }
//
//    fun getProductsByIsleID(): LiveData<List<ProductsResponse>>{
//        return products
//    }
}