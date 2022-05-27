package me.yokan.ingest.feed.source

import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader
import me.yokan.ingest.model.ArticleData
import me.yokan.ingest.model.FeedResult
import java.io.IOException
import java.net.URL
import java.util.Date

interface RSSFeed : FeedSource {

    val endpoint: URL

    override suspend fun query(): FeedResult? {
        return try {
            val feed = SyndFeedInput().build(XmlReader(endpoint))
            FeedResult(
                id,
                (feed.publishedDate ?: Date()).toInstant().toEpochMilli(), // TODO: Is published date sufficient?
                parseEntries(feed)
            )
        } catch(ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    @Suppress("unchecked_cast")
    private fun parseEntries(feed: SyndFeed) = ((feed.entries as? List<SyndEntry>) ?: error("Failed to cast feed entries for ${feed.title} (${feed.uri} to SyndEntry list..."))
        .map { entry ->
            ArticleData(
                id = entry.uri,
                timestamp = (entry.updatedDate ?: Date()).toInstant().toEpochMilli(), // TODO: Is published date sufficient?
                title = entry.title,
                description = entry.description.value ?: "No content.....",
                source = URL(entry.uri),
                author = entry.author
            )
        }
}