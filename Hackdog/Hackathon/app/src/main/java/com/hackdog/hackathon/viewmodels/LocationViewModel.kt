package com.hackdog.hackathon.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel(){
    private var nearestSection = MutableLiveData<String>()


    fun setNearestSection(sec: String){
        if(sec != ""){
            nearestSection.value = sec
        }
    }

    fun getNearestSection() : LiveData<String> {
        return nearestSection
    }


    private var grocery = MutableLiveData<String>()

    fun setGrocery(sec: String){
        if(sec != ""){
            grocery.value = sec
        }
    }

    fun getGrocery() : LiveData<String> {
        return grocery
    }
}