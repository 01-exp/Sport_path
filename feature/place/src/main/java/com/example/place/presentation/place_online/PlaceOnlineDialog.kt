package com.example.place.presentation.place_online


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.place.R
import com.example.place.databinding.PlaceOnlineDialogBinding
import com.example.place.presentation.PlaceViewModel
import com.example.place.presentation.PlaceViewModelFactory
import com.example.place.presentation.Utils
import com.example.place.presentation.di.provider.PlaceComponentProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import kotlin.properties.Delegates

class PlaceOnlineDialog : DialogFragment() {



    private lateinit var binding: PlaceOnlineDialogBinding

    @Inject
    lateinit var placeViewModelFactory: PlaceViewModelFactory
    private lateinit var viewModel: PlaceViewModel



    private lateinit var date: String
    private var placeId by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())
        binding = PlaceOnlineDialogBinding.inflate(layoutInflater)

        (requireActivity().application as PlaceComponentProvider)
            .getPlaceComponent().inject(this)
        viewModel = ViewModelProvider(this, placeViewModelFactory)[PlaceViewModel::class.java]

        date = Utils.getTodayDate()
        placeId = arguments?.getInt("id")!!

        binding.dateTextView.text = Utils.formattedDate(date)

        binding.dateTextView.setOnClickListener {
            showDatePickerDialog(Calendar.getInstance())
        }

        viewModel.placeOnlineList.observe(this) {
            Log.d("mLogPlaceOnline",it.toString())
            setUpRecyclerView(it)
            binding.noPeopleTextView.isVisible = it.isEmpty()

        }
        viewModel.getPlaceOnline(placeId, date)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun setUpRecyclerView(placeOnlineMap: Map<String, Int>) {
        binding.OnLineRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.OnLineRecyclerView.adapter = PlaceOnlineAdapter(placeOnlineMap.toList())
    }

    @SuppressLint("SimpleDateFormat")
    fun showDatePickerDialog(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val minDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day}.${month + 1}.${year}")
        val maxDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day + 30}.${month + 1}.${year}")

        context?.let {
            DatePickerDialog(
                it, R.style.Theme_Sport_path_DatePicker,
                { _, myYear, myMonth, myDay ->

                    date = if ((myMonth + 1).toString().length < 2) {
                        "${myDay}.0${myMonth + 1}.${myYear}"
                    } else {
                        "${myDay}.${myMonth + 1}.${myYear}"
                    }
                   viewModel.getPlaceOnline(placeId, date)
                    binding.dateTextView.text = Utils.formattedDate(date)

                }, year, month, day,
            ).apply {
                if (minDateUnix != null && maxDateUnix != null) {

                    datePicker.minDate = minDateUnix.time
                    datePicker.maxDate = maxDateUnix.time
                    show()
                }
            }

        }
    }
}