package com.hackdog.hackathon.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BarcodeViewModel : ViewModel(){

    private var barcodeScanned = MutableLiveData<String>()

    fun setBarcodeScanned(code: String){
        barcodeScanned.value = code
    }

    fun getBarcodeScanned() : LiveData<String>{
        return barcodeScanned
    }
}