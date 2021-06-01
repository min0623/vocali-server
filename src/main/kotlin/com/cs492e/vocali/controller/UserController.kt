package com.cs492e.vocali.controller

import com.cs492e.vocali.model.*
import com.cs492e.vocali.repository.UserRepository
import com.cs492e.vocali.repository.SelectedSongRepository
import com.cs492e.vocali.repository.SongRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("/users")
@CrossOrigin("*") // TOOD: 프론트 배포된 환경에서만 허용하기
class UserController {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var selectedSongRepository: SelectedSongRepository
    @Autowired
    private lateinit var songRepository: SongRepository

    @PostMapping
    @ResponseBody
    fun addNewUser(@RequestBody user: User): User {
        return User().apply {
            this.name = user.name
            this.age = user.age
            this.minPitch = user.minPitch
            this.maxPitch = user.maxPitch
            this.pitchWeight = user.pitchWeight
            this.moodWeight = user.moodWeight
            this.prefWeight = user.prefWeight
        }.run {
            userRepository.save(this)
        }
    }

    @GetMapping("/{userId}")
    @ResponseBody
    fun getUserById(@PathVariable userId: Int): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user")
    }

    @GetMapping
    @ResponseBody
    fun getAllUsers(): Iterable<User> = userRepository.findAll()

    @PutMapping("/{userId}")
    @ResponseBody
    fun updateUser(@PathVariable userId: Int,
                   @RequestBody request: UserRequest): User {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user")

        request.run {
            name?.let { user.name = it }
            age?.let { user.age = it }
            minPitch?.let { user.minPitch = it }
            maxPitch?.let { user.maxPitch = it }
        }
        userRepository.save(user)

        return user
    }

    @PostMapping("/songs")
    @ResponseBody
    fun addSongs(@RequestBody songs: List<Song>): String {
        songRepository.saveAll(songs)
        return "Success"
    }

    @PostMapping("/{userId}/songs/select")
    @ResponseBody
    fun addSelectedSongs(@PathVariable userId: Int, @RequestBody requests: List<SelectedSongRequest>): String {
        val user = userRepository.findById(userId).get()
        requests.forEach { request ->
            val song = songRepository.findByIdOrNull(request.id)
            SelectedSong().apply {
                this.category = SelectedSong.Category.getCategoryFromString(request.category)
                this.user = user
                this.song = song
            }.run {
                selectedSongRepository.save(this)
            }
        }

        return "Success"
    }

    @GetMapping("/{userId}/songs/select")
    @ResponseBody
    fun getSelectedSongs(@PathVariable userId: Int): Iterable<SelectedSong> {
        val user = userRepository.findById(userId).get()
        return selectedSongRepository.findAllByUser(user.id)
    }

    @GetMapping("/{userId}/recommendations")
    @ResponseBody
    fun getRecommendation(
        @PathVariable userId: Int,
        @RequestParam(required = false) moods: List<String>,
        @RequestParam(required = false, defaultValue = "5") per: Int): Iterable<Song> {
        print(moods)
        return songRepository.findAll().toList().subList(0, per)
    }
}