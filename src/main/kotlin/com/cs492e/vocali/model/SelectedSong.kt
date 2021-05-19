package com.cs492e.vocali.model

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
class SelectedSong {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var name: String = ""

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var user: User? = null
}