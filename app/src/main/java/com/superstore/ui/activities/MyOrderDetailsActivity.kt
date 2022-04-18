package com.superstore.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.superstore.R
import com.superstore.models.Order
import com.superstore.utils.Constants
import kotlinx.android.synthetic.main.activity_my_order_details.*

/**
 * My Order Details Screen.
 */
class MyOrderDetailsActivity : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_my_order_details)

        //  function to setup action bar.

        setupActionBar()


        //  Get the order details through intent.

        val myOrderDetails: Order
        if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)) {
            myOrderDetails =
                intent.getParcelableExtra<Order>(Constants.EXTRA_MY_ORDER_DETAILS)!!
        }

    }

    // function to setup action bar.

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_my_order_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_my_order_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

}