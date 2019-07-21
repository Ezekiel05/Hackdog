package com.hackdog.hackathon

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hackdog.hackathon.databinding.FragmentLoginBinding
import com.hackdog.hackathon.databinding.FragmentMainMenuBinding
import com.hackdog.hackathon.viewmodels.LocationViewModel
import com.hackdog.hackathon.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment : Fragment() {

    private lateinit var loginViewModel: LocationViewModel

    private lateinit var mBinding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false)

        loginViewModel = ViewModelProviders.of(activity!!).get(LocationViewModel::class.java)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner

        return mBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        spinner

        val loc = listOf("","Grocery A", "Grocery B")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, loc)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.spinner.adapter = adapter


        mBinding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                if(position != 0 ){
                    loginViewModel.setGrocery(parent.getItemAtPosition(position).toString())
                    val action = MainMenuFragmentDirections.actionMainMenuFragmentToRangeDetectorFragment()
                    findNavController().navigate(action)
                }
//                Spinner selected : ${parent.getItemAtPosition(position).toString()}"
            }

            override fun onNothingSelected(parent: AdapterView<*>){

            }
                // Another interface callback

    }

    mBinding.groceryList.setOnClickListener {
        val action = MainMenuFragmentDirections.actionMainMenuFragmentToShoppingListFragment()
        findNavController().navigate(action)
    }

        mBinding.help.setOnClickListener {
            val action = MainMenuFragmentDirections.actionMainMenuFragmentToHelpFragment()
            findNavController().navigate(action)
        }

    }
}
