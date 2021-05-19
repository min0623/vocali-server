package com.cs492e.vocali.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""

    var minPitch: String = ""

    var maxPitch: String = ""

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    var selectedSongs: List<SelectedSong> = emptyList()
}