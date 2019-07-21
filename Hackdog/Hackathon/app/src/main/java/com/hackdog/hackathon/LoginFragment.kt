package com.hackdog.hackathon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hackdog.hackathon.databinding.FragmentLoginBinding
import com.hackdog.hackathon.viewmodels.BarcodeViewModel
import com.hackdog.hackathon.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var mBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false)

        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.login.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToMainMenuFragment()
            findNavController().navigate(action)
        }
    }
}