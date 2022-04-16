package com.superstore.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.superstore.R
import com.superstore.firestore.FirestoreClass
import com.superstore.models.Cart
import com.superstore.models.Product
import com.superstore.utils.Constants
import com.superstore.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : BaseActivity(),  View.OnClickListener {

    //global variable for product ID
    private var mProductId: String = ""
    //lobal instance of the Product data class which will be initialized later
    private lateinit var mProductDetails: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
        }

        //Get the product owner id through intent
        var productOwnerId: String = ""

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            productOwnerId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }

        //call  setupActionBar()
        setupActionBar()
        //if product owner id is the same as user ID then add to cart button doesn't show
        if (FirestoreClass().getCurrentUserID() == productOwnerId) {
            btn_add_to_cart.visibility = View.GONE
            btn_go_to_cart.visibility = View.GONE

        } else {
            btn_add_to_cart.visibility = View.VISIBLE
        }

        //call on click listner for add to cart
        btn_add_to_cart.setOnClickListener(this)
        //call on click listner for go to cart
        btn_go_to_cart.setOnClickListener(this)

        //retrieve product details when activity is launched
        getProductDetails()


    }


    //actionbar created(for the back button)
    private fun setupActionBar() {

        setSupportActionBar(toolbar_product_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_product_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to notify the success result of the product details based on the product id.
     *
     * @param product A model class with product details.
     */
    fun productDetailsSuccess(product: Product) {
        //initialize mProductDetails
        mProductDetails = product


        // Populate the product details in the UI.
        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
            product.image,
            iv_product_detail_image
        )

        tv_product_details_title.text = product.title
        tv_product_details_price.text = "€${product.price}"

        tv_product_details_description.text = product.description
        tv_product_details_stock_quantity.text = product.stock_quantity

        // There is no need to check the cart list if the product owner himself is seeing the product details.
        if (FirestoreClass().getCurrentUserID() == product.user_id) {
            // Hide Progress dialog.
            hideProgressDialog()
        } else {
            FirestoreClass().checkIfItemExistInCart(this@ProductDetailsActivity, mProductId)
        }
    }

    //function to call the firestore class function that will get the product details from cloud firestore based on the product id
    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FirestoreClass to get the product details.
        FirestoreClass().getProductDetails(this@ProductDetailsActivity, mProductId)
    }

    override fun onClick(v: View?) {
        //iff add to cart button was clicked
        if (v != null) {
            when (v.id) {
                //on click listner to add product to cart
                R.id.btn_add_to_cart -> {
                    addToCart()
                }
                //click listner to send user to cart list activity
                R.id.btn_go_to_cart->{
                    startActivity(Intent(this@ProductDetailsActivity, CartListActivity::class.java))
                }
            }
        }
    }
    //function to prepare cart item if added to the cart
    private fun addToCart() {

        val addToCart = Cart(
            FirestoreClass().getCurrentUserID(),
            mProductId,
            mProductDetails.title,
            mProductDetails.price,
            mProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )
        //call function in firestore class to add cart items to cloud firestore
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().addCartItems(this@ProductDetailsActivity, addToCart)
    }

    //function to notify item is successfully added to the cart
    fun addToCartSuccess() {
        // Hide the progress dialog.
        hideProgressDialog()

        Toast.makeText(
            this@ProductDetailsActivity,
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_SHORT
        ).show()

        // Hide the AddToCart button if the item is already in the cart.
        btn_add_to_cart.visibility = View.GONE
        // Show the GoToCart button if the item is already in the cart. User can update the quantity from the cart list screen if he wants.
        btn_go_to_cart.visibility = View.VISIBLE
    }

    //function to notify that items exist in the cart
    fun productExistsInCart() {

        // Hide the progress dialog.
        hideProgressDialog()

        // Hide the AddToCart button if the item is already in the cart.
        btn_add_to_cart.visibility = View.GONE
        // Show the GoToCart button if the item is already in the cart. User can update the quantity from the cart list screen if he wants.
        btn_go_to_cart.visibility = View.VISIBLE
    }

}