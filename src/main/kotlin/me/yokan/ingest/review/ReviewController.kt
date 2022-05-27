package me.yokan.ingest.review

import me.yokan.ingest.data.FeedDataSource
import me.yokan.ingest.data.SerializationController
import me.yokan.ingest.model.QueuedSubmissions
import me.yokan.ingest.parse.ParseController
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException


@RestController
class ReviewController(
    private val data: FeedDataSource,
    private val mapper: SerializationController,
    private val parseController: ParseController
) {

    @GetMapping("/api/feed")
    fun feed() = mapper.toJson(data.nextArticle())

    @PostMapping("/api/mark/{source}")
    fun review(@PathVariable("source") source: String, @RequestParam("id") id: String, @RequestParam("flagged") flagged: Boolean) {
        val result = data.markReviewed(source, id, flagged)
        result.error?.let {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, it)
        }
    }

    @GetMapping("/api/content/{source}")
    suspend fun content(@PathVariable("source") source: String, @RequestParam("id") id: String) =
        data.articleInfo(source, id)?.let { (source, data) -> parseController.parseContent(source, data) }
            ?.let { mapper.toJson(it) }
            ?: throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "Article not found")


    @GetMapping("/api/queued")
    suspend fun reports() = data.retrieveMarked()
        .mapNotNull { (src, data) -> parseController.parseContent(src, data) }
        .let { QueuedSubmissions(it) }
        .let { mapper.toJson(it) }
}