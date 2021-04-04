package com.mariusbudin.sampleclean.data.episodes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mariusbudin.sampleclean.data.episodes.model.remote.EpisodeRemoteModel

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episodes")
    fun getEpisodes(): LiveData<List<EpisodeRemoteModel>>

    @Query("SELECT * FROM episodes WHERE id = :id")
    fun getEpisode(id: Int): LiveData<EpisodeRemoteModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EpisodeRemoteModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: EpisodeRemoteModel)
}