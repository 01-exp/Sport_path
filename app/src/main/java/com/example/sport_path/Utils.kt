package com.example.sport_path

import com.example.sport_path.data_structures.Sport
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
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
        Sport("Баскетбол", "basketball", R.drawable.backetball),
        Sport("Футбол", "football", R.drawable.soccerball),
        Sport("Волейбол", "volleyball", R.drawable.tennis),
        Sport("Воркаут", "workout", R.drawable.walleyball),

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


}