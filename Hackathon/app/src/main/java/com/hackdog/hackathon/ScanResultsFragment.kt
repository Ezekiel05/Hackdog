package com.hackdog.hackathon

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hackdog.hackathon.databinding.FragmentScanResultsBinding
//import com.hackdog.hackathon.databinding.Fra
import com.hackdog.hackathon.viewmodels.BarcodeViewModel
import com.hackdog.hackathon.viewmodels.ShoppingListViewModel


class ScanResultsFragment : Fragment() {

    private lateinit var shoppingListViewModel: ShoppingListViewModel
    private lateinit var barcodeViewModel: BarcodeViewModel

    private lateinit var mBinding: FragmentScanResultsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        shoppingListViewModel = ViewModelProviders.of(activity!!).get(ShoppingListViewModel::class.java)
        barcodeViewModel = ViewModelProviders.of(activity!!).get(BarcodeViewModel::class.java)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_results, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        barcodeViewModel.getBarcodeScanned().observe(this, Observer {

            val x = shoppingListViewModel.getProductByBarcode(it)

            x.observe(this, Observer { res ->
                if(res != null){
                    mBinding.productName.text = res.name
                    mBinding.description.text = res.isle?.name
                    mBinding.price.text = res.price.toString()
                    mBinding.stocks.text = res.stocks.toString()
                }
            })
        })

        mBinding.add.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        mBinding.cancel.setOnClickListener {
            fragmentManager?.popBackStack()
        }


    }

}
