package com.cs492e.vocali.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="song", schema = "public")
class Song {

    @Id
    var id: String = ""

    var title: String = ""

    var artist: String = ""

    var genre: String = ""

    var publishedYear: Int = 0
}