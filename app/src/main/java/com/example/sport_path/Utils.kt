package com.example.sport_path

import com.example.sport_path.data_structures.Sport
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import java.lang.IndexOutOfBoundsException
import java.util.Calendar

object Utils {
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
        return try {
            word + " " + splitAddress[splitAddress.indexOf(word) + 1]
        } catch (e: IndexOutOfBoundsException) {
            word
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
        return "${dateSplit[0]} $month"
    }
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        if ((month+1).toString().length<2){
            return "${day}.0${month + 1}.${year}"
        }
        return "${day}.${month + 1}.${year}"
    }
}

