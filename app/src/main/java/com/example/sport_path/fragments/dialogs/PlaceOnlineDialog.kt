package com.example.sport_path.fragments.dialogs


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.sport_path.Utils
import com.example.sport_path.services.maps.PlaceOnlineAdapter
import com.example.sport_path.services.maps.PlaceViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

abstract class PlaceOnlineDialog(private val viewModel:PlaceViewModel,context:Context?, private val placeId:Int, private val owner: LifecycleOwner) : Dialog(context!!) {


    private lateinit var dateTextView: TextView
    private lateinit var noPeopleTextView: TextView
    private lateinit var date:String








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())
        val view = LayoutInflater.from(context).inflate(R.layout.place_online_dialog, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)




        dateTextView = view.findViewById(R.id.dateTextView)
        noPeopleTextView = view.findViewById(R.id.noPeopleTextView)


        date = Utils.getTodayDate()

        dateTextView.text = Utils.formattedDate(date)
        dateTextView.setOnClickListener {
            showDatePickerDialog(Calendar.getInstance())
        }

        viewModel.placeOnlineList.observe(owner){
            setUpRecyclerView(view,it)
            noPeopleTextView.isVisible = !it.isNotEmpty()

        }
        viewModel.getPlaceOnline(placeId,date)


    }

    private fun setUpRecyclerView(view: View,placeOnlineMap:  Map<String, Int>){
        val recyclerView =view.findViewById<RecyclerView>(R.id.OnLineRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PlaceOnlineAdapter(placeOnlineMap.toList())

    }
    @SuppressLint("SimpleDateFormat")
    fun showDatePickerDialog(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val minDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day}.${month + 1}.${year}")
        val maxDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day + 30}.${month + 1}.${year}")

        DatePickerDialog(
            context,
            { _, myYear, myMonth, myDay ->

                date = if ((myMonth + 1).toString().length < 2) {
                    "${myDay}.0${myMonth + 1}.${myYear}"
                } else {
                    "${myDay}.${myMonth + 1}.${myYear}"
                }
                viewModel.getPlaceOnline(placeId, date)
               dateTextView.text = Utils.formattedDate(date)

            }, year, month, day
        ).apply {
            if (minDateUnix != null && maxDateUnix != null) {
                datePicker.minDate = minDateUnix.time
                datePicker.maxDate = maxDateUnix.time
                show()
            }
        }
    }
}