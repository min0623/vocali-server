package com.cs492e.vocali.repository

import com.cs492e.vocali.model.SelectedSong
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface SelectedSongRepository: CrudRepository<SelectedSong, Int> {

    @Query("FROM SelectedSong s where s.user.id = :userId")
    fun findAllByUser(@Param("userId") userId: Int?): Iterable<SelectedSong>
}