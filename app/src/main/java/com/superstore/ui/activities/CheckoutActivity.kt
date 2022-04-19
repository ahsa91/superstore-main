package com.superstore.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.superstore.R
import com.superstore.firestore.FirestoreClass
import com.superstore.models.Address
import com.superstore.models.Cart
import com.superstore.models.Order
import com.superstore.models.Product
import com.superstore.ui.adapters.CartItemsListAdapter
import com.superstore.utils.Constants
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : BaseActivity() {

    // A global variable for the selected address details.
    private var mAddressDetails: Address? = null
    //Global variable for all product list.
    private lateinit var mProductsList: ArrayList<Product>
    //Global variable for cart items list
    private lateinit var mCartItemsList: ArrayList<Cart>

    // A global variable for the SubTotal Amount.
    private var mSubTotal: Double = 0.0

    // A global variable for the Total Amount.
    private var mTotalAmount: Double = 0.0

    // A global variable for Order details.
    private lateinit var mOrderDetails: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        //call sety=upActionBar()
        setupActionBar()

        //et the selected address details through intent.
        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
            mAddressDetails =
                intent.getParcelableExtra<Address>(Constants.EXTRA_SELECTED_ADDRESS)!!
        }
        //Set the selected address details to UI that is received through intent.
        if (mAddressDetails != null) {
            tv_checkout_address_type.text = mAddressDetails?.type
            tv_checkout_full_name.text = mAddressDetails?.name
            tv_checkout_address.text = "${mAddressDetails!!.address}, ${mAddressDetails!!.postCode}"
            tv_checkout_additional_note.text = mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                tv_checkout_other_details.text = mAddressDetails?.otherDetails
            }
            tv_checkout_mobile_number.text = mAddressDetails?.mobileNumber
        }

//click event to the btn place order and call the function
        btn_place_order.setOnClickListener {
            placeAnOrder()
        }
        //get product list to populate UI
        getProductList()
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_checkout_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_checkout_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to get product list to compare the current stock with the cart items.
     */
    private fun getProductList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAllProductsList(this@CheckoutActivity)
    }

    /**
     * A function to get the success result of product list.
     *
     * @param productsList
     */
    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

        //Initialize the global variable of all product list.
        mProductsList = productsList


        //Call the function to get the latest cart items.
        getCartItemsList()
    }

    //function to get the latest cart items.
    private fun getCartItemsList() {

        FirestoreClass().getCartList(this@CheckoutActivity)
    }

    /**
     * A function to notify the success result of the cart items list from cloud firestore.
     *
     * @param cartList
     */
    fun successCartItemsList(cartList: ArrayList<Cart>) {

        // Hide progress dialog.
        hideProgressDialog()

        //Update the stock quantity in the cart list from the product list.

        for (product in mProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stock_quantity
                }
            }
        }


        mCartItemsList = cartList

        //Populate the cart items in the UI.

        rv_cart_list_items.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        rv_cart_list_items.setHasFixedSize(true)

        //  Pass the required param.
        val cartListAdapter = CartItemsListAdapter(this@CheckoutActivity, mCartItemsList, false)
        rv_cart_list_items.adapter = cartListAdapter


        //  Calculate the subtotal and Total Amount.

        for (item in mCartItemsList) {

            val availableQuantity = item.stock_quantity.toInt()

            if (availableQuantity > 0) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                mSubTotal += (price * quantity)
            }
        }

        tv_checkout_sub_total.text = "€$mSubTotal"
        // Here we have kept Shipping Charge is fixed as €10 but in your case it may cary
        tv_checkout_shipping_charge.text = "€10.0"

        if (mSubTotal > 0) {
            ll_checkout_place_order.visibility = View.VISIBLE

            mTotalAmount = mSubTotal + 10.0
            tv_checkout_total_amount.text = "€$mTotalAmount"
        } else {
            ll_checkout_place_order.visibility = View.GONE
        }

    }

    /**
     * A function to prepare the Order details to place an order.
     */
    private fun placeAnOrder() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        //order details based on all the required details.

            mOrderDetails = Order(
            FirestoreClass().getCurrentUserID(),
            mCartItemsList,
            mAddressDetails!!,
            "My order ${System.currentTimeMillis()}",
            mCartItemsList[0].image,
            mSubTotal.toString(),
            "10.0", // The Shipping Charge is fixed as $10 for now in our case.
            mTotalAmount.toString(),
            //set timestamp of order
            System.currentTimeMillis()
        )


        // function to place the order in the cloud firestore.

        FirestoreClass().placeOrder(this@CheckoutActivity, mOrderDetails)

    }



    //function to notify the success result of the order placed.

    /**
     * A function to notify the success result of the order placed.
     */
    fun orderPlacedSuccess() {

        FirestoreClass().updateAllDetails(this@CheckoutActivity, mCartItemsList, mOrderDetails)
    }

    /**
     * A function to notify the success result after updating all the required details.
     */
    fun allDetailsUpdatedSuccessfully() {


        // Hide the progress dialog.
        hideProgressDialog()

        Toast.makeText(this@CheckoutActivity, "Your order placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

    }


}