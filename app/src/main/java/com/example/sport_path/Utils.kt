package com.example.sport_path

import android.util.Log
import com.example.sport_path.data_structures.Sport
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import java.lang.IndexOutOfBoundsException
import java.text.SimpleDateFormat
import java.util.Calendar

object Utils {

    //    fun getApiKey(): String {
//        val properties = Properties()
//        properties.load(
//            FileInputStream(
//                File("C:\\Users\\dekkv\\AndroidStudioProjects\\Sport_path\\local.properties")
//
//            )
//        )
//
//        return properties.getProperty("apiKey")
//    }
    val Sports = listOf(
        Sport("Баскетбол", "basketball", R.drawable.basketball),
        Sport("Футбол", "football", R.drawable.football),
        Sport("Волейбол", "volleyball", R.drawable.volleyball),
        Sport("Воркаут", "workout", R.drawable.gym),
        Sport("Теннис", "tennis", R.drawable.tennis),
        Sport("Cкейтбординг", "skate_park", R.drawable.skateboard),
    )

    val startPosition = CameraPosition(
        Point(47.237422, 39.712262),
        /* zoom = */ 12f,
        /* azimuth = */0.0f,
        /* tilt = */ 0.0f
    )

    val timePoints = listOf(
        "07:00",
        "07:30",
        "08:00",
        "08:30",
        "09:00",
        "09:30",
        "10:00",
        "10:30",
        "11:00",
        "11:30",
        "12:00",
        "12:30",
        "13:00",
        "13:30",
        "14:00",
        "14:30",
        "15:00",
        "15:30",
        "16:00",
        "16:30",
        "17:00",
        "17:30",
        "18:00",
        "18:30",
        "19:00",
        "19:30",
        "20:00",
        "20:30",
        "21:00",
        "21:30",
        "22:00",
        "22:30",
        "23:00",
        "23:30"
    )

    fun getIconBySport(sportName: String): Int {
        Sports.forEach {
            if (it.nameEn == sportName) {
                return it.icon
            }
        }
        return Sports[0].icon
    }

    fun cutAddress(address: String): String {
        val steets = listOf("улица", "площадь", "переулок", "проспект", "бульвар")

        val splitAddress = address
            .replace("ул.", "улица ")
            .removePrefix("Ростов-на-Дону,")
            .split(",")
        var word = ""
        splitAddress.forEach {
            steets.forEach { street ->
                if (street in it.lowercase()) {
                    word = it
                }
            }
        }
//        Log.d("mlog", "$address  $word $splitAddress")
        try {
//            Log.d("mlog", word + " ," + splitAddress[splitAddress.indexOf(word) + 1]+"123")
            return word + " " + splitAddress[splitAddress.indexOf(word) + 1]

        } catch (e: IndexOutOfBoundsException) {
            return word
        }
    }

    fun formattedDate(date: String): String {
        val dateSplit = date.split('.').map { it.toInt() }
        val month = when (dateSplit[1]) {
            1 -> "Января"
            2 -> "Февраля"
            3 -> "Марта"
            4 -> "Апреля"
            5 -> "Мая"
            6 -> "Июня"
            7 -> "Июля"
            8 -> "Августа"
            9 -> "Сентября"
            10 -> "Октября"
            11 -> "Ноября"
            12 -> "Декабря"


            else -> "Декабря"
        }

        // улица площаль переулок проспект

        return "${dateSplit[0]} $month"
    }


}