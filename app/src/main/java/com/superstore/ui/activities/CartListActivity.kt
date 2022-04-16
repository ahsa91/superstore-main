package com.superstore.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.superstore.R
import com.superstore.firestore.FirestoreClass
import com.superstore.models.Cart
import com.superstore.ui.adapters.CartItemsListAdapter
import kotlinx.android.synthetic.main.activity_cart_list.*

//cart list activity of the app
class CartListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        //call setup actionbar
        setupActionBar()
    }
    //back button function
    private fun setupActionBar() {

        setSupportActionBar(toolbar_cart_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_cart_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    //function to notify the success result of the cart items list from cloud firestore.

    fun successCartItemsList(cartList: ArrayList<Cart>) {

        // Hide progress dialog.
        hideProgressDialog()

        if (cartList.size > 0) {

            rv_cart_items_list.visibility = View.VISIBLE
            ll_checkout.visibility = View.VISIBLE
            tv_no_cart_item_found.visibility = View.GONE

            rv_cart_items_list.layoutManager = LinearLayoutManager(this@CartListActivity)
            rv_cart_items_list.setHasFixedSize(true)

            val cartListAdapter = CartItemsListAdapter(this@CartListActivity, cartList)
            rv_cart_items_list.adapter = cartListAdapter

            var subTotal: Double = 0.0

            for (item in cartList) {

                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                subTotal += (price * quantity)
            }

            tv_sub_total.text = "€$subTotal"
            // Here we have kept Shipping Charge is fixed as 10
            tv_shipping_charge.text = "€10.0"

            if (subTotal > 0) {
                ll_checkout.visibility = View.VISIBLE

                val total = subTotal + 10
                tv_total_amount.text = "€$total"
            } else {
                ll_checkout.visibility = View.GONE
            }

        } else {
            rv_cart_items_list.visibility = View.GONE
            ll_checkout.visibility = View.GONE
            tv_no_cart_item_found.visibility = View.VISIBLE
        }
    }

    //function to get cart items list
    private fun getCartItemsList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getCartList(this@CartListActivity)
    }

    override fun onResume() {
        super.onResume()

        getCartItemsList()
    }
}