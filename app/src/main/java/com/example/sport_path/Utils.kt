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
        Sport("Баскетбол", R.drawable.backetball),
        Sport("Футбол", R.drawable.soccerball),
        Sport("Карбюратор", R.drawable.tennis),
        Sport("инжектор", R.drawable.walleyball),
        Sport("инспектор", R.drawable.backetball),
        Sport("проректор", R.drawable.soccerball),
        Sport("ИК-порт", R.drawable.tennis),
        Sport("блютуз", R.drawable.walleyball),
        Sport("туз", R.drawable.backetball),
        Sport("козырь", R.drawable.soccerball),
        Sport("валет", R.drawable.tennis),
        Sport("дама", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),
        Sport("румба", R.drawable.walleyball),



    )

    val startPosition = CameraPosition(
        Point(47.237422, 39.712262),
        /* zoom = */ 17.5f,
        /* azimuth = */0.0f,
        /* tilt = */ 0.0f
    )


}