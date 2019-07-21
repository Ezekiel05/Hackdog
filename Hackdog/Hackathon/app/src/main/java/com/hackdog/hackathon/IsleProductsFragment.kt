package com.hackdog.hackathon

//import com.hackdog.hackathon.databinding.Fra
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hackdog.hackathon.databinding.FragmentIsleProductsBinding
import com.hackdog.hackathon.viewmodels.BarcodeViewModel
import com.hackdog.hackathon.viewmodels.ShoppingListViewModel


class IsleProductsFragment : Fragment() {

    private lateinit var shoppingListViewModel: ShoppingListViewModel
    private lateinit var barcodeViewModel: BarcodeViewModel

    private lateinit var mBinding: FragmentIsleProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        shoppingListViewModel = ViewModelProviders.of(activity!!).get(ShoppingListViewModel::class.java)
        barcodeViewModel = ViewModelProviders.of(activity!!).get(BarcodeViewModel::class.java)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_isle_products, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        mBinding.add.setOnClickListener {
//            fragmentManager?.popBackStack()
//        }
//
//        mBinding.cancel.setOnClickListener {
//            fragmentManager?.popBackStack()
//        }
        shoppingListViewModel
    }

}
