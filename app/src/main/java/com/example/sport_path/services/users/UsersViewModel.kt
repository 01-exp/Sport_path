package com.example.sport_path.services.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Place
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel() : ViewModel() {


    private val   userIdMutable : MutableLiveData<Int> = MutableLiveData()
    val userId: LiveData<Int> = userIdMutable

    private val entriesListMutable: MutableLiveData<List<Entry>> = MutableLiveData()
    val entriesList: LiveData<List<Entry>> =  entriesListMutable


    fun getUserId(){
        viewModelScope.launch {
            val userId = withContext(Dispatchers.IO) {
                ServiceLocator.getService<Storage>("Storage")?.getUserId()
            }
            userIdMutable.value = userId!!
        }
    }




    fun getUserEntries(id:Int){
        viewModelScope.launch {
            val entriesList = withContext(Dispatchers.IO) {
                ServiceLocator.getService<UserManager>("UserManager")?.getUserEntries(id)
            }
            entriesListMutable.value = entriesList!!
        }
    }
    fun setEntry(placeId: Int,time:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ServiceLocator.getService<UserManager>("UserManager")?.setEntry(placeId, time)
            }
        }
    }
}

class UsersViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UsersViewModel() as T

}