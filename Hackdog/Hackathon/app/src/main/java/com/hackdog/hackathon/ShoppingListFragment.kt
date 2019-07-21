package com.hackdog.hackathon

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackdog.hackathon.databinding.FragmentShoppingListBinding
import com.hackdog.hackathon.models.Isle
import com.hackdog.hackathon.models.Products
import com.hackdog.hackathon.models.ProductsResponse
import com.hackdog.hackathon.models.Supermarket
import com.hackdog.hackathon.viewmodels.BarcodeViewModel
import com.hackdog.hackathon.viewmodels.ShoppingListViewModel
import kotlinx.android.synthetic.main.fragment_isle_products.*
import java.lang.StringBuilder

class ShoppingListFragment : Fragment() {

    private lateinit var shoppingListViewModel: ShoppingListViewModel
    private lateinit var barcodeViewModel: BarcodeViewModel

    private lateinit var mBinding: FragmentShoppingListBinding

    private val barcodeScannerFragment = BarcodeScannerFragment()
    private  var prodId = arrayListOf<String>()
    private var myCart = listOf<Products>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        shoppingListViewModel = ViewModelProviders.of(activity!!).get(ShoppingListViewModel::class.java)
        barcodeViewModel = ViewModelProviders.of(activity!!).get(BarcodeViewModel::class.java)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_list, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.button2.setOnClickListener {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 123)

            if (!barcodeScannerFragment.isAdded) {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.add(R.id.barcodeScannerLayout, barcodeScannerFragment)
                transaction?.commit()
                transaction?.addToBackStack(null)
            }
        }

        barcodeViewModel.getBarcodeScanned().observe(this, Observer {

            val x = shoppingListViewModel.getProductByBarcode(it)

            x.observe(this, Observer { res ->

                val product = Products(
                    res.id,
                    res.barcode,
                    res.name,
                    res.price,
                    res.expirationDate,
                    res.stocks!! - 1,
                    Isle(res.isle?.id, res.isle?.name, Supermarket(res.isle?.superMarket?.id, res.isle?.superMarket?.name)),
                    1,
                    false,
                    res.price
                )

                val msg = StringBuilder()
                if(res != null){
                    msg.append(res.name)
                        .append("\n")
                        .append(res.isle?.name)
                        .append("\n")
                        .append(res.price)
                        .append("\n")
                        .append(res.stocks!!)
                        .append("\n")
                }
                val alertDialog = AlertDialog.Builder(activity)
                    //set icon
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    //set title
                    .setTitle("Add to Cart?")
                    //set message
                    .setMessage(msg)
                    //set positive button
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, i ->
                        //set what would happen when positive button is clicked
                        Toast.makeText(activity, "added to cart", Toast.LENGTH_LONG).show()
                        shoppingListViewModel.addToCart(product!!)
                        dialog.cancel()
                    })
                    //set negative button
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        Toast.makeText(activity, "cancelled", Toast.LENGTH_LONG).show()
                        dialogInterface.cancel()
                    })
                    .show()
            })
        })

        val linearLayoutManager = LinearLayoutManager(this.context)
        mBinding.cartRecyclerView.layoutManager = linearLayoutManager

        shoppingListViewModel.getCartList().observe(this, Observer {
            mBinding.cartRecyclerView.adapter =
                CartRecyclerAdapter(it) { img: Products, s: String -> productClicked(img, s) }

            myCart = it

        })


        shoppingListViewModel.getAllProducts().observe(this, Observer {
            val list = arrayListOf<String>()
             prodId = arrayListOf<String>()
            it?.forEachIndexed { index, res ->
                if(!list.contains(res.name!!))
                {
                    list.add(res.name!!)
                    prodId.add(res.barcode!!)
                }
            }

                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
//                    ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, it.getOrNull()!!)
                mBinding.searchSuggestions.setAdapter(adapter)
        })

        mBinding.searchSuggestions.threshold = 1
        mBinding.searchSuggestions.setOnFocusChangeListener { _, b ->
            if (b) mBinding.searchSuggestions.showDropDown()
        }
        mBinding.searchSuggestions.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            barcodeViewModel.setBarcodeScanned(prodId.get(position))
        }

//        mBinding.totalCost.text =
    }


    fun productClicked(item : Products, s: String) {
        Toast.makeText(context, "item clicked $s", Toast.LENGTH_SHORT).show()
        if(s == "minus"){
            if(item.quantity!! >= 1)
            {
                item.quantity = item.quantity!! - 1
                item.stocks = item.stocks!! + 1
            }
        }
        else if(s == "add"){
            if(item.stocks!! >= 0)
                {
                    item.quantity = item.quantity!! + 1
                    item.stocks = item.stocks!! - 1
                }
        }
        item.totalPrice = item.price!! * item.quantity!!

        var totalCost = 0.0
        myCart?.forEach {
            if(it.checked!!){
                totalCost += it.totalPrice!!
            }
        }
        mBinding.totalCost.text = totalCost.toString()

        mBinding.cartRecyclerView.adapter?.notifyDataSetChanged()

    }
}
