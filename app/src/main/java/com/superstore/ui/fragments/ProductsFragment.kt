package com.superstore.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.superstore.R
import com.superstore.firestore.FirestoreClass
import com.superstore.models.Product
import com.superstore.ui.activities.AddProductActivity
import com.superstore.ui.adapters.MyProductsListAdapter
import kotlinx.android.synthetic.main.fragment_products.*


class ProductsFragment : BaseFragment() {


    /*private lateinit var homeViewModel: HomeViewModel*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_products, container, false)
        return root
    }
    //function that inflates add product menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //function that moves user to add product activity screen
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_add_product) {
            startActivity(Intent(activity, AddProductActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onResume() {
        super.onResume()

        getProductListFromFireStore()
    }

    private fun getProductListFromFireStore() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of Firestore class.
        FirestoreClass().getProductsList(this@ProductsFragment)
    }

    /**
     * A function to get the successful product list from cloud firestore.
     *
     * @param productsList Will receive the product list from cloud firestore.
     */
    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
       // Hide Progress dialog.
       hideProgressDialog()

       if (productsList.size > 0) {
           rv_my_product_items.visibility = View.VISIBLE
           tv_no_products_found.visibility = View.GONE

           rv_my_product_items.layoutManager = LinearLayoutManager(activity)
           rv_my_product_items.setHasFixedSize(true)

//            Pass the third parameter value.

           val adapterProducts =
               MyProductsListAdapter(requireActivity(), productsList, this@ProductsFragment)

           rv_my_product_items.adapter = adapterProducts
       } else {
           rv_my_product_items.visibility = View.GONE
           tv_no_products_found.visibility = View.VISIBLE
       }

    }

    fun deleteProduct(productID: String) {

        // Here we will call the delete function of the FirestoreClass. But, for now lets display the Toast message and call this function from adapter class.

        Toast.makeText(
            requireActivity(),
            "You can now delete the product. $productID",
            Toast.LENGTH_SHORT
        ).show()
    }



}