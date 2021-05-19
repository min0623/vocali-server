package com.cs492e.vocali.controller

import com.cs492e.vocali.repository.UserRepository
import com.cs492e.vocali.model.SelectedSong
import com.cs492e.vocali.model.User
import com.cs492e.vocali.repository.SelectedSongRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var selectedSongRepository: SelectedSongRepository

    @PostMapping
    @ResponseBody
    fun addNewUser(@RequestBody user: User): User {
        return User().apply {
            this.name = user.name
        }.run {
            userRepository.save(this)
        }
    }

    @GetMapping
    @ResponseBody
    fun getAllUsers(): Iterable<User> = userRepository.findAll()

    @PostMapping("/{userId}/selected_songs")
    @ResponseBody
    fun addSelectedSongs(@PathVariable userId: Int, @RequestBody songs: List<SelectedSong>): String {
        val user = userRepository.findById(userId).get()
        songs.forEach {
            SelectedSong().apply {
                this.name = it.name
                this.user = user
            }.run {
                selectedSongRepository.save(this)
            }
        }


        return "Success"
    }

    @GetMapping("/{userId}/selected_songs")
    @ResponseBody
    fun getSelectedSongs(@PathVariable userId: Int): Iterable<SelectedSong> {
        val user = userRepository.findById(userId).get()
        return selectedSongRepository.findAllByUser(user.id)
    }
}