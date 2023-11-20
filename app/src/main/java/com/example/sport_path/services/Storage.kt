package com.example.sport_path.services

import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.google.android.gms.common.api.Response
import com.yandex.mapkit.geometry.Point
import java.net.URL

class Storage() {
    fun getPlaceList(sport: Sport): List<Place> {
        val placeList = mutableListOf<Place>()
        when (sport.name) {
            "Баскетбол" -> {
                placeList.add(Place(Point(47.237422, 39.712262), "Глааный Корпус"))
                placeList.add(Place(Point(47.23668641935381, 39.71187635010748), "Сквер"))
            }

            "Футбол" -> placeList.add(
                Place(
                    Point(47.239467141531335, 39.7124612930925),
                    "Общежитие"
                )
            )

            "Карбюратор" -> placeList.add(
                Place(
                    Point(47.242212995994976, 39.71151033401034),
                    "Парк ДГТУ"
                )
            )

        }
        return placeList
    }




    val AllplaceList = listOf(
        Place(Point(47.237422, 39.712262), "Главный Корпус"),
        Place(Point(47.23668641935381, 39.71187635010748), "Сквер"),
        Place(Point(47.239467141531335, 39.7124612930925), "Общежитие"),
        Place(Point(47.242212995994976, 39.71151033401034), "Парк ДГТУ")
    )


}