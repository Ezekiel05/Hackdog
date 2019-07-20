package com.hackdog.hackathon.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hackdog.hackathon.models.ProductsResponse
import com.hackdog.hackathon.models.ProductsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
//
//                    val x = Coordinates(
//                        lat = ProductsResponse.results?.get(0)?.geometry?.location?.lat!!,
//                        long = ProductsResponse.results?.get(0)?.geometry?.location?.lng!!
//                    )

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

    companion object {
        var BaseUrl = "http://27c87f70.ngrok.io/api/v1/"
    }
}