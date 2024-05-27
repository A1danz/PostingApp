package com.a1danz.feature_places_info.data.mapper

import android.util.Log
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import javax.inject.Inject

class PostPlaceTypeMapper @Inject constructor() {

    fun mapDomainToData(postPlaceType: PostPlaceType): String {
        return when(postPlaceType) {
            PostPlaceType.VK_PAGE -> PostPlaceType.VK_PAGE.name
            PostPlaceType.TG -> PostPlaceType.TG.name
            PostPlaceType.VK_GROUP -> PostPlaceType.VK_GROUP.name
        }
    }

    fun mapDataToDomain(placeString: String): PostPlaceType? {
        return when(placeString) {
            PostPlaceType.VK_PAGE.name -> PostPlaceType.VK_PAGE
            PostPlaceType.VK_GROUP.name -> PostPlaceType.VK_GROUP
            PostPlaceType.TG.name -> PostPlaceType.TG
            else ->  {
                Log.e("ERROR", "UNCATCHABLE PostPlaceType $placeString")
                null
            }
        }
    }

}