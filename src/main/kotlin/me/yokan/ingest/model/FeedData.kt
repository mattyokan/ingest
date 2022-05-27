package me.yokan.ingest.model

import org.springframework.data.annotation.Id

/**
 * A data model representing the persistent storage
 * relevant to a feed source.
 */
data class FeedData(
    @Id val id: String,
    val lastChecked: Long,
    val articles: Map<String, ArticleData>
)