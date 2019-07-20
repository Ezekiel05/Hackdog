package com.hackdog.hackathon

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_scan_results.*
import java.lang.StringBuilder

class ShoppingListFragment : Fragment() {

    private lateinit var shoppingListViewModel: ShoppingListViewModel
    private lateinit var barcodeViewModel: BarcodeViewModel

    private lateinit var mBinding: FragmentShoppingListBinding

    private val barcodeScannerFragment = BarcodeScannerFragment()

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
                        shoppingListViewModel.addToCart(product)
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
        })
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

        mBinding.cartRecyclerView.adapter?.notifyDataSetChanged()
    }
}
