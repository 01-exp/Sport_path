package com.example.entry.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.entry.domain.EntryRepository
import com.example.entry.data.data_structures.Entry
import com.example.entry.data.data_structures.UserInfo
import kotlinx.coroutines.launch

class EntryViewModel(private val repository: EntryRepository) : ViewModel() {

    private val userIdMutable: MutableLiveData<Int> = MutableLiveData()
    val userId: LiveData<Int> = userIdMutable

    private val entriesListMutable: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val entriesList: LiveData<MutableList<Entry>> = entriesListMutable

    private val loginCodeMutable: MutableLiveData<UserInfo> = MutableLiveData()
    val loginCode: LiveData<UserInfo> = loginCodeMutable




    fun getUserEntries() {
        viewModelScope.launch {
            repository.getUserEntries(entriesListMutable)
        }
    }

    fun deleteEntry(id: Int, position: Int) {
        viewModelScope.launch {
            repository.deleteEntry(id)
            entriesList.value?.removeAt(position)
        }
    }


}
class EntryViewModelFactory(private val entryRepository: EntryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        EntryViewModel(entryRepository) as T
}