package com.superstore.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.superstore.R
import kotlinx.android.synthetic.main.dialog_progress.*


/**
 * Base fragment with functions to use in other fragments.
 */
open class BaseFragment : Fragment() {
    //progress dialog instance intialized later
    private lateinit var mProgressDialog: Dialog

    //hide progress dialog
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    //showing circular progress dialog with text

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }


}