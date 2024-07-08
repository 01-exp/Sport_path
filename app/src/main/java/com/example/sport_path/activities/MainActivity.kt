package com.example.sport_path.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.sport_path.R
import com.example.sport_path.application.appComponent
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var routerFactory :Router.Factory
    lateinit var router: Router

    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent.inject(this)

        router = routerFactory.create(supportFragmentManager)
        viewModel = ViewModelProvider(this,usersViewModelFactory)[UsersViewModel::class.java]

        if (viewModel.isUserLogged()) {
            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MAP)
        } else {
            router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_LOGIN)
        }
    }


}
