package me.yokan.ingest.model

data class FeedResult(
    val sourceId: String,
    val timestamp: Long,
    val articles: List<ArticleData>
)