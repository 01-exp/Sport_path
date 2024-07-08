package com.example.sport_path.fragments.bottomSheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.sport_path.application.appComponent
import com.example.sport_path.databinding.FragmentProfileBottomSheetDialogBinding
import com.example.sport_path.fragments.dialogs.ConfirmFragmentDialog
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class ProfileBottomSheetDialogFragment : BottomSheetDialogFragment(),
    ConfirmFragmentDialog.onClickListener {

    private lateinit var binding: FragmentProfileBottomSheetDialogBinding
    private lateinit var confirmDialog: ConfirmFragmentDialog


    @Inject
    lateinit var routerFactory :Router.Factory
    lateinit var router: Router

    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory
    private lateinit var viewModel: UsersViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        router = routerFactory.create(parentFragmentManager)
        viewModel = ViewModelProvider(this,usersViewModelFactory)[UsersViewModel::class.java]

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBottomSheetDialogBinding.inflate(layoutInflater)
        binding.UserNameTextView.text = viewModel.getUserName()
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
    override fun onClick() {
        out()
    }
    private fun out() {
        router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_LOGIN)
        viewModel.userOut()
        dismiss()
    }

}