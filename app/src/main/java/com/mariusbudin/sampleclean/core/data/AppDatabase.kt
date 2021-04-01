package com.mariusbudin.sampleclean.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mariusbudin.sampleclean.features.characters.data.CharactersDao
import com.mariusbudin.sampleclean.features.characters.data.model.Character
import com.mariusbudin.sampleclean.features.episodes.data.EpisodesDao
import com.mariusbudin.sampleclean.features.episodes.data.model.Episode

@Database(entities = [Character::class, Episode::class], version = 1, exportSchema = false)
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