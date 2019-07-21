package com.hackdog.hackathon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hackdog.hackathon.models.Products
import kotlinx.android.synthetic.main.my_cart_recycler_view.view.*
import kotlinx.android.synthetic.main.products_layout.view.*

class ProductsRecyclerAdapter(private val url : List<Products>, private val listener: (Products) -> Unit ) : RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>() {
    override fun getItemCount() : Int = url.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(url[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.products_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Products, listener: (Products) ->  Unit) {

            itemView.name.text = item.name

        }
    }
}