package com.superstore.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

/**
 * A custom object to declare all the constant values in a single file. The constant values declared here is can be used in whole application.
 */
object Constants {


    // Firebase Constants
    // This  is used for the collection name for USERS.
    const val USERS: String = "users"
    //This  is used for the collection name for products.
    const val PRODUCTS: String = "products"
    //This  is used for the collection name for USERS.
    const val CART_ITEMS: String = "cart_items"
    //This  is used for the collection name for ADDRESSES
    const val ADDRESSES: String = "addresses"

    const val SUPERSTORE_PREFERENCES: String = "SuperstorePrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    // Intent extra constants.
    const val EXTRA_USER_DETAILS: String = "extra_user_details"

    //A unique code for asking the Read Storage Permission using this we will be check and identify in the method onRequestPermissionsResult in the Base Activity.
    const val READ_STORAGE_PERMISSION_CODE = 2

    // A unique code of image selection from Phone Storage.
    const val PICK_IMAGE_REQUEST_CODE = 2

    // Constant variables for Gender
    const val MALE: String = "Male"
    const val FEMALE: String = "Female"
    const val NA: String="Not Applicable"

    // Firebase database field names
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val IMAGE: String = "image"
    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"
    const val COMPLETE_PROFILE: String = "profileCompleted"

    const val USER_PROFILE_IMAGE:String = "User_Profile_Image"
    const val PRODUCT_IMAGE: String = "Product_Image"

    const val USER_ID: String = "user_id"

    const val PRODUCT_ID: String = "product_id"

    const val EXTRA_PRODUCT_ID: String = "extra_product_id"
    //product owner id
    const val EXTRA_PRODUCT_OWNER_ID: String = "extra_product_owner_id"
    //constant variable for default cart quantity
    const val DEFAULT_CART_QUANTITY: String = "1"
    //constant variable for cart_quantity
    const val CART_QUANTITY: String = "cart_quantity"

    //constants for address types
    const val HOME: String = "Home"
    const val OFFICE: String = "Office"
    const val OTHER: String = "Other"
    //constant value to pass the address details through intent.
    const val EXTRA_ADDRESS_DETAILS: String = "AddressDetails"
    //constant to pass the value through intent in the address listing screen which will help to select the address to proceed with checkout
    const val EXTRA_SELECT_ADDRESS: String = "extra_select_address"
    //global constant variable to notify the add address
    const val ADD_ADDRESS_REQUEST_CODE: Int = 121
    //contant variable to pass the address details to the checkout screen through intent.
    const val EXTRA_SELECTED_ADDRESS: String = "extra_selected_address"
    //constant for Orders collection.
    const val ORDERS: String = "orders"
    //constant for Stock Quantity.
    const val STOCK_QUANTITY: String = "stock_quantity"
    //constant for passing the order details through intent.
    const val EXTRA_MY_ORDER_DETAILS: String = "extra_MY_ORDER_DETAILS"







    /**
     * A function for user profile image selection from phone storage.
     */
    fun showImageChooser(activity: Activity) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    /**
     * A function to get the image file extension of the selected image.
     *
     * @param activity Activity reference.
     * @param uri Image file uri.
     */
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }


}