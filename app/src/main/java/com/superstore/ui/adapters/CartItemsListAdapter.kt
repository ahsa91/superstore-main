package com.superstore.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.superstore.R
import com.superstore.models.Cart
import com.superstore.utils.GlideLoader
import kotlinx.android.synthetic.main.item_cart_layout.view.*


//adapter class for CartItemsList
class CartItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Cart>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //inflates item_cart_layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cart_layout,
                parent,
                false
            )
        )
    }
    //inflate
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //model is the cart item
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadProductPicture(model.image, holder.itemView.iv_cart_item_image)

            holder.itemView.tv_cart_item_title.text = model.title
            holder.itemView.tv_cart_item_price.text = "€${model.price}"
            holder.itemView.tv_cart_quantity.text = model.cart_quantity
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}