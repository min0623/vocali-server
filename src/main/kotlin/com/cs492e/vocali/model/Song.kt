package com.cs492e.vocali.model

import org.hibernate.annotations.ColumnDefault
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

    @ColumnDefault("0")
    var songNum: Int = 0
}