package com.mariusbudin.sampleclean.data.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mariusbudin.sampleclean.data.characters.CharactersDao
import com.mariusbudin.sampleclean.data.characters.model.remote.CharacterRemoteModel
import com.mariusbudin.sampleclean.data.episodes.EpisodesDao
import com.mariusbudin.sampleclean.data.episodes.model.remote.EpisodeRemoteModel

@Database(
    entities = [CharacterRemoteModel::class, EpisodeRemoteModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharactersDao
    abstract fun episodesDao(): EpisodesDao

    companion object {

        private const val DB_NAME = "characters_db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}