package com.example.sport_path.services

import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.yandex.mapkit.geometry.Point

class Storage() {
    fun getPlaceList(sport:Sport):List<Place>{
        val placeList = mutableListOf<Place>()
        when(sport.name){
            "Баскетбол" ->{placeList.add(Place(Point(47.237422, 39.712262), "Поршкеян тут" , 123))
            placeList.add(Place(Point(47.23668641935381, 39.71187635010748), "негр с газонокосилкой", 135))
            }
            "Футбол" -> placeList.add( Place(Point(47.239467141531335, 39.7124612930925), "Джувенс", 457))
            "Карбюратор" -> placeList.add( Place(Point(47.242212995994976, 39.71151033401034), "я тут срал", 789))

        }
        return placeList
    }

    val AllplaceList = listOf(
        Place(Point(47.237422, 39.712262), "Поршкеян тут", 123),
        Place(Point(47.23668641935381, 39.71187635010748), "негр с газонокосилкой", 135),
        Place(Point(47.239467141531335, 39.7124612930925), "Джувенс", 457),
        Place(Point(47.242212995994976, 39.71151033401034), "я тут срал", 789)
    )

}