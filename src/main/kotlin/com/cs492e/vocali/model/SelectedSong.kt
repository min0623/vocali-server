package com.cs492e.vocali.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonCreator
import java.lang.Exception
import javax.persistence.*

@Entity
@Table(name="selected_song", schema = "public")
class SelectedSong {

    enum class Category {
        LIKE, DISLIKE;

        companion object {

            fun getCategoryFromString(value: String): Category {
                return try {
                    valueOf(value.toUpperCase())
                } catch (e: Exception) {
                    LIKE
                }
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    var category: Category = Category.LIKE

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var user: User? = null

    @ManyToOne
    @JoinColumn(name = "song_id")
    var song: Song? = null
}