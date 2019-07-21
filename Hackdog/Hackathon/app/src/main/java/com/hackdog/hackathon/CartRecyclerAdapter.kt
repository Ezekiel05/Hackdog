package com.hackdog.hackathon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hackdog.hackathon.models.Products
import kotlinx.android.synthetic.main.fragment_shopping_list.view.*
import kotlinx.android.synthetic.main.my_cart_recycler_view.view.*

class CartRecyclerAdapter(private val url : List<Products>, private val listener: (Products, String) -> Unit ) : RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>() {
    override fun getItemCount() : Int = url.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(url[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.my_cart_recycler_view, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Products, listener: (Products, String) ->  Unit) {

//            Glide.with(itemView.context)
//                .load(item)
//                .centerCrop()
//                .into(itemView.imageView)
            itemView.productName.text = item.name
            itemView.details.text = item.isle?.name
            itemView.price.text = item.totalPrice.toString()
            itemView.quantity.text = item.quantity.toString()

            itemView.decreaseBtn.setOnClickListener {
//                if(item.quantity!! >= 1)
//                {
//                    item.quantity = item.quantity!! - 1
//                    item.stocks = item.stocks!! + 1
//                    Toast.makeText(itemView.context, "item decreased", Toast.LENGTH_SHORT).show()
//                }
                listener(item, "minus")
            }
            itemView.increaseBtn.setOnClickListener {
//                if(item.stocks!! >= 0)
//                {
//                    item.quantity = item.quantity!! + 1
//                    item.stocks = item.stocks!! - 1
//                }
//                Toast.makeText(itemView.context, "item increased ", Toast.LENGTH_SHORT).show()
                listener(item, "add")
            }

            item.checked = itemView.checkBox.isChecked
            itemView.checkBox.setOnClickListener { listener(item, "") }

            itemView.productDetailsLayout.setOnClickListener { listener(item, "") }
        }
    }
}