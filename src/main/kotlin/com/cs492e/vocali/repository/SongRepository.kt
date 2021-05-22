package com.cs492e.vocali.repository

import com.cs492e.vocali.model.Song
import org.springframework.data.repository.CrudRepository

interface SongRepository: CrudRepository<Song, String> {
}