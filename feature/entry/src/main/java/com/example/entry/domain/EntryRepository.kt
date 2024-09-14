package com.example.entry.domain

import androidx.lifecycle.MutableLiveData
import com.example.entry.data.data_structures.Entry

interface EntryRepository {

    fun getUserEntries(entriesListMutableLiveData: MutableLiveData<MutableList<Entry>>)

    fun deleteEntry(entryId: Int)
}