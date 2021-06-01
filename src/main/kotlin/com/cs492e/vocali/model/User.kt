package com.cs492e.vocali.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.annotations.ColumnDefault
import javax.persistence.*

@Entity
@Table(name = "user", schema = "public")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""

    @ColumnDefault("0")
    var age: Int = 0

    var minPitch: String = ""

    var maxPitch: String = ""

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    var selectedSongs: List<SelectedSong> = emptyList()

    @ColumnDefault("0.5")
    var pitchWeight = 0.5

    @ColumnDefault("0.5")
    var moodWeight = 0.5

    @ColumnDefault("0.5")
    var prefWeight = 0.5

}