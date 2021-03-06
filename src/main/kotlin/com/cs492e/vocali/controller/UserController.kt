package com.cs492e.vocali.controller

import com.cs492e.vocali.DefaultProperties
import com.cs492e.vocali.model.*
import com.cs492e.vocali.repository.SelectedSongRepository
import com.cs492e.vocali.repository.SongRepository
import com.cs492e.vocali.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import kotlin.math.min

@Controller
@Component
@RequestMapping("/users")
class UserController {

    companion object {
        const val MODEL_URL = "http://143.248.235.5:15000"
    }

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var selectedSongRepository: SelectedSongRepository
    @Autowired
    private lateinit var songRepository: SongRepository
    @Autowired
    private lateinit var properties: DefaultProperties

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
            pitchWeight?.let { user.pitchWeight = it }
            moodWeight?.let { user.moodWeight = it }
            prefWeight?.let { user.prefWeight = it }
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
        @RequestParam(required = false, defaultValue = "5") per: Int): Iterable<RecommendationResponse> {
        val user = userRepository.findById(userId).get()
        val selectedSongs = selectedSongRepository.findAllByUser(userId)
        val requestBody = RecommendationRequest(
            prefWeight = user.prefWeight.toFloat(),
            moodWeight = user.moodWeight.toFloat(),
            pitchWeight = user.pitchWeight.toFloat(),
            likeList = selectedSongs.filter { it.category == SelectedSong.Category.LIKE }
                .map { it.song?.id }
                .requireNoNulls(),
            dislikeList = selectedSongs.filter { it.category == SelectedSong.Category.DISLIKE }
                .map { it.song?.id }
                .requireNoNulls(),
            undefinedList = selectedSongs.filter { it.category == SelectedSong.Category.UNDEFINED }
                .map { it.song?.id }
                .requireNoNulls(),
            minPitch = user.minPitch,
            maxPitch = user.maxPitch,
            moods = moods
        )
        val restTemplate = RestTemplate()
        val result = restTemplate.postForObject(
            "$MODEL_URL/recommendations",
            requestBody,
            Array<ModelResponse>::class.java
        )

        val songs = songRepository.findAllById(result?.map { it.id }.orEmpty())
        return result?.let { recommendations ->
            val count = min(per, recommendations.size)
            recommendations.toList().subList(0, count).map { rec ->
                val song = songs.find { it.id == rec.id } ?: return@map null
                return@map RecommendationResponse(song, rec)
            }.filterNotNull()
        } ?: emptyList()
    }
}