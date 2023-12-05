package com.example.sport_path

import com.example.sport_path.data_structures.Sport
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

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
        Sport("Баскетбол","basketball", R.drawable.backetball),
        Sport("Футбол","football", R.drawable.soccerball),
        Sport("Волейбол","valleyball", R.drawable.tennis),
        Sport("Воркаут","workout", R.drawable.walleyball),

    )

    val startPosition = CameraPosition(
        Point(47.237422, 39.712262),
        /* zoom = */ 12f,
        /* azimuth = */0.0f,
        /* tilt = */ 0.0f
    )


}