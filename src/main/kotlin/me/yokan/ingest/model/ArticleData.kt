package me.yokan.ingest.model

import java.net.URL

/**
 * Represents a data model of an article which was retrieved
 * from some [FeedSource].
 */
data class ArticleData(
    /*
    The internal ID of this article.
     */
    val id: String,
    /**
     * The publication time of the article.
     */
    val timestamp: Long,

    /**
     * The author(s) of the article.
     */
    val author: String,

    /**
     * The title of the article.
     */
    val title: String,

    /**
     * A short description of the article.
     */
    val description: String,


    /**
     * A scrape-able URL of the source.
     */
    val source: URL,

    /**
     * Whether an article has been reviewed...
     */
    val reviewed: Boolean = false,

    /**
     * Whether a given reviewed article is flagged for extraction.
     */
    val flagged: Boolean? = null,
)