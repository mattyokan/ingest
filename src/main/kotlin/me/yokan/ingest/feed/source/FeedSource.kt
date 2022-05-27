package me.yokan.ingest.feed.source

import me.yokan.ingest.model.FeedResult

/**
 * Represents a retrievable feed source. A feed source provides some feed
 * of article metadata which can be stored for use within the review endpoints.
 */
interface FeedSource {

    val id: String

    val baseUrl: String


    suspend fun query(): FeedResult?


}