package com.mariusbudin.sampleclean.data.common

import androidx.room.TypeConverter
import com.mariusbudin.sampleclean.data.characters.model.remote.CharacterLocationRemoteModel
import com.mariusbudin.sampleclean.data.characters.model.remote.Status

class Converters {
    @TypeConverter
    fun toStatus(value: String) =
        enumValues<Status>().firstOrNull { it.name == value } ?: Status.UNKNOWN

    @TypeConverter
    fun fromStatus(value: Status) = value.name

    @TypeConverter
    fun toLocation(value: String) = CharacterLocationRemoteModel(value, "")

    @TypeConverter
    fun fromLocation(value: CharacterLocationRemoteModel) = value.name

    @TypeConverter
    fun toCharacters(value: String) = value.split(SEPARATOR)

    @TypeConverter
    fun fromCharacters(value: List<String>) = value.joinToString(SEPARATOR)

    companion object {
        const val SEPARATOR = "|"
    }
}