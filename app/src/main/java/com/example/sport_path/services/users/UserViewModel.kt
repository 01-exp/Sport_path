package com.example.sport_path.services.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.maps.data.data_structures.Entry
import com.example.maps.data.data_structures.UserInfo
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UserRepository) : ViewModel() {

    private val userIdMutable: MutableLiveData<Int> = MutableLiveData()
    val userId: LiveData<Int> = userIdMutable

    private val entriesListMutable: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val entriesList: LiveData<MutableList<Entry>> = entriesListMutable

    private val loginCodeMutable: MutableLiveData<UserInfo> = MutableLiveData()
    val loginCode: LiveData<UserInfo> = loginCodeMutable
    fun userOut() {
        viewModelScope.launch {
            repository.userOut(loginCodeMutable)
        }
    }
    fun regNewUser(userName: String, login: String, password: String) {
        viewModelScope.launch {
            repository.getNewUser(userName, login, password, userIdMutable)
        }
    }
    fun getUserName(): String =
        repository.getUserName()


    fun isUserLogged(): Boolean =
        repository.isUserLogged()

    fun getUserEntries() {
        viewModelScope.launch {
            repository.getUserEntries(entriesListMutable)
        }
    }
    fun setEntry(placeId: Int, time: String) {
        viewModelScope.launch {
            repository.setEntry(placeId, time)
        }
    }
    fun deleteEntry(id: Int, position: Int) {
        viewModelScope.launch {
            repository.deleteEntry(id)
        }
    }

    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(login, password, loginCodeMutable)
        }
    }
}
class UsersViewModelFactory(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        UsersViewModel(userRepository) as T
}