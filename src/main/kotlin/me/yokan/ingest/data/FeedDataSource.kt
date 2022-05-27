package me.yokan.ingest.data

import me.yokan.ingest.feed.source.FeedSource
import me.yokan.ingest.model.ArticleData
import me.yokan.ingest.model.FeedData
import me.yokan.ingest.model.ReviewResult

enum class ReviewState(val error: String? = null) {
    Valid,
    UnknownSource("Unknown source ID"),
    UnknownArticle("Unknown article ID")
}

interface FeedDataSource {

    suspend fun retrieve(sources: List<FeedSource>): Map<FeedSource, FeedData>

    suspend fun put(source: FeedSource, data: FeedData)

    fun nextArticle(): ReviewResult?

    fun retrieveMarked(): List<Pair<FeedSource, ArticleData>>

    fun markReviewed(sourceId: String, id: String, flagged: Boolean): ReviewState

    fun articleInfo(sourceId: String, id: String): Pair<FeedSource, ArticleData>?
}