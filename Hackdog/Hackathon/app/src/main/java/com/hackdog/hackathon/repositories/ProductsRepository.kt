package com.hackdog.hackathon.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hackdog.hackathon.models.ProductsResponse
import com.hackdog.hackathon.models.ProductsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.reflect.TypeToken
import com.hackdog.hackathon.models.Products

class ProductsRepository {

    fun getCurrentData(barcode: String): LiveData<ProductsResponse> {

        val products =  MutableLiveData<ProductsResponse>()

        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsService::class.java)
        val call = service.getProductsByBarcode(barcode)

        Log.d("products service", "retrofit called")


        call.enqueue(object : Callback<ProductsResponse> {
            override fun onResponse(call: Call<ProductsResponse>, response: retrofit2.Response<ProductsResponse>) {
                if (response.code() == 200) {


                    val ProductsResponse = response.body()!!

                        products.value = ProductsResponse

                    Log.d("products service", "retrofit called")

                    Log.d("products service", ProductsResponse.name)
                }
            }
            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                Log.d("weather", "--error " + t.localizedMessage)
            }
        })
        return products
    }

    fun getAllProducts(): LiveData<List<ProductsResponse>> {

        val products =  MutableLiveData<List<ProductsResponse>>()

        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsService::class.java)
        val call = service.getAllProducts()

        call.enqueue(object : Callback<List<ProductsResponse>> {
            override fun onResponse(call: Call<List<ProductsResponse>>, response: retrofit2.Response<List<ProductsResponse>>) {
                if (response.code() == 200) {

                    val productsResponse = response.body()!!
                    products.value = productsResponse

                    Log.d("tag", "sadas " + productsResponse.size.toString())
                }
            }
            override fun onFailure(call: Call<List<ProductsResponse>>, t: Throwable) {
                Log.d("weather", "--error " + t.localizedMessage)
            }
        })
        return products
    }

    companion object {
        var BaseUrl = "http://27c87f70.ngrok.io/api/v1/"
    }

    fun addToCart(product: Products){



        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsService::class.java)
        val call = service.addToCart("test", product.id!!, product.quantity!!, false )

        Log.d("products service","jhsd " +  product.id)


        call.enqueue(object : Callback<ProductsResponse> {
            override fun onResponse(call: Call<ProductsResponse>, response: retrofit2.Response<ProductsResponse>) {
                if (response.code() == 200) {


//                    val ProductsResponse = response.body()!!
//
//                    products.value = ProductsResponse
//
                    Log.d("products service", "raddes")
//
//                    Log.d("products service", ProductsResponse.name)
                }
            }
            override fun onFailure(call: Call<ProductsResponse>, t: Throwable) {
                Log.d("weather", "--error " + t.localizedMessage)
            }
        })
    }

    fun getProductsByIsleID(id: Int): LiveData<List<ProductsResponse>> {

        val products =  MutableLiveData<List<ProductsResponse>>()

        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ProductsService::class.java)
        val call = service.getProductsByIsleId(id)

        call.enqueue(object : Callback<List<ProductsResponse>> {
            override fun onResponse(call: Call<List<ProductsResponse>>, response: retrofit2.Response<List<ProductsResponse>>) {
                if (response.code() == 200) {

                    val productsResponse = response.body()!!
                    products.value = productsResponse

                    Log.d("tag", "sadas " + productsResponse.size.toString())
                }
            }
            override fun onFailure(call: Call<List<ProductsResponse>>, t: Throwable) {
                Log.d("weather", "--error " + t.localizedMessage)
            }
        })
        return products
    }
}