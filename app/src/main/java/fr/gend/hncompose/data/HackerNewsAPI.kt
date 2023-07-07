package fr.gend.hncompose.data

import retrofit2.http.GET
import retrofit2.http.Path

data class HNItem (
    val by: String,
    val kids: List<Int>,
    val score: Int,
    val time: Long,
    val title: String,
    val url: String?,
    val text: String?,
    val descendants: Int?
):java.io.Serializable

interface HackerNewsAPI {
    @GET("topstories.json")
    suspend fun topStories(): List<Int>

    @GET("item/{id}.json")
    suspend fun item(@Path("id") id: Int): HNItem
}

