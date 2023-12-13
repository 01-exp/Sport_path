package com.example.sport_path.services.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.data_structures.Place
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel() : ViewModel() {

    private val   placeListMutable : MutableLiveData<List<Place>> = MutableLiveData()

    val placeList: LiveData<List<Place>> = placeListMutable

    private val   userIdMutable : MutableLiveData<Int> = MutableLiveData()
    val userId: LiveData<Int> = userIdMutable
    private val storage = ServiceLocator.getService<Storage>("Storage")

    fun getUserId(){
        viewModelScope.launch {
            val userId = withContext(Dispatchers.IO) {
                storage?.getUserId()
            }
            userIdMutable.value = userId!!
        }
    }
}

class UsersViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UsersViewModel() as T

}