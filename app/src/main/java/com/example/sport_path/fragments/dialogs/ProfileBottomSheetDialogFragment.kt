package com.example.sport_path.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sport_path.R
import com.example.sport_path.databinding.FragmentProfileBottomSheetDialogBinding
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.users.UsersViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ProfileBottomSheetDialogFragment : BottomSheetDialogFragment(),ConfirmFragmentDialog.onClickListener {

    lateinit var binding: FragmentProfileBottomSheetDialogBinding
    lateinit var storage: Storage
    lateinit var confirmDialog: ConfirmFragmentDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBottomSheetDialogBinding.inflate(layoutInflater)
        storage = ServiceLocator.getService("Storage")!!
        binding.UserNameTextView.text = storage.getUserName()
        binding.outButton.setOnClickListener {

            confirmDialog = object : ConfirmFragmentDialog(context,this) {

            }
            confirmDialog.show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    fun openFragment(fragment: Fragment) {
        ServiceLocator.getService<Router>("Router")?.addFragment(fragment, false)
    }


    fun out() {

        storage.clearUserData()
        ServiceLocator.getService<FragmentFactory>("FragmentFactory")?.createFragment(
            FragmentFactory.FRAGMENT_LOGIN
        ).let {
            openFragment(it!!)
        }
        ServiceLocator.getService<UsersViewModel>("UsersViewModel").let {
            it!!.userOut()
        }
        dismiss()
    }

    override fun onClick() {
        out()
    }

}