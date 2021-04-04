package com.mariusbudin.sampleclean.data.characters.model.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "characters")
data class CharacterRemoteModel(
    @PrimaryKey val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val location: CharacterLocationRemoteModel,
    val image: String
) {
    companion object {
        val empty =
            CharacterRemoteModel(0, "", Status.UNKNOWN, "", CharacterLocationRemoteModel.empty, "")
    }
}

enum class Status {
    @Json(name = "Alive")
    ALIVE,

    @Json(name = "Dead")
    DEAD,

    @Json(name = "unknown")
    UNKNOWN
}