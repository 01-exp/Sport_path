package com.example.entry.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.entry.data.EntryStorage
import com.example.entry.data.data_structures.Entry
import com.example.entry.data.retrofit.Interface.EntryRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntryRepositoryImpl(
    private val retrofitService: EntryRetrofitService,
    private val storage: EntryStorage
) : EntryRepository {
    override fun getUserEntries(entriesListMutableLiveData: MutableLiveData<MutableList<Entry>>) {
        val id = storage.getUserId()
        retrofitService.getUserEntries(id).enqueue(
            object : Callback<MutableList<Entry>> {
                override fun onResponse(
                    call: Call<MutableList<Entry>>,
                    response: Response<MutableList<Entry>>
                ) {
                    if (response.isSuccessful) {
                        entriesListMutableLiveData.value = response.body()
                        Log.d("mLog", "GetEntries")
                    } else {
                        Log.d("mLogERR", "ELSEGetEntries")
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<Entry>>,
                    throwable: Throwable
                ) {
                    Log.d("mLogERR", "onFailureGetEntirues    $throwable")
                }
            }
        )

    }





    override fun deleteEntry(entryId: Int) {
        retrofitService.deleteEntry(entryId).enqueue(
            object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                }

                override fun onFailure(
                    call: Call<String>,
                    throwable: Throwable
                ) {
                }
            }
        )

    }


}