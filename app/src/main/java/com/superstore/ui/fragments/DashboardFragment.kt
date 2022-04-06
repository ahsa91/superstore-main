package com.superstore.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.superstore.R
import com.superstore.ui.activities.SettingsActivity

class DashboardFragment : Fragment() {


    /*private lateinit var dashboardViewModel: DashboardViewModel*/
    //inflate option menu in fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)*/

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.text = "This is dashboard Fragment"

        /*dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    //INFLATE dashboard_menu layout
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //function to direct user to settings activity upon
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {

            R.id.action_settings -> {
                //direct user to settings activity upon selection
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

