package com.cs492e.vocali.model

class SelectedSongResponse {
    var id = ""
    var title = ""
    var artist = ""
    var genre = ""
    var publishedYear = 0
    var category = ""

    fun addSongInfo(song: Song) {
        this.id = song.id
        this.title = song.title
        this.artist = song.artist
        this.genre = song.genre
        this.publishedYear = song.publishedYear
    }
}