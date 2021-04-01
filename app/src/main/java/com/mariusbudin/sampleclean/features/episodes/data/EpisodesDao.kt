package com.mariusbudin.sampleclean.features.episodes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mariusbudin.sampleclean.features.episodes.data.model.Episode

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episodes")
    fun getEpisodes(): LiveData<List<Episode>>

    @Query("SELECT * FROM episodes WHERE id = :id")
    fun getEpisode(id: Int): LiveData<Episode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: Episode)
}