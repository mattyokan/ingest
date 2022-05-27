package me.yokan.ingest.feed

import me.yokan.ingest.data.FeedDataSource
import me.yokan.ingest.feed.source.FeedSource
import me.yokan.ingest.model.FeedData
import me.yokan.ingest.model.FeedResult
import org.springframework.stereotype.Component

@Component
class FeedController(
    private val sources: List<FeedSource>,
    private val data: FeedDataSource,
) {

    /**
     * Query all feed sources.
     *
     * This operation will query all feed sources for metadata, retrieve the existing
     * data from the persistent storage, and update with any changes.
     */
    suspend fun queryAll() {
        println("Querying all feed sources")
        val queried = sources
            .mapNotNull { src -> src.query()?.let { src to it } }
            .toMap()
        data.retrieve(sources)
            .map { (src, data) ->
                val updated = queried[src]?.let { result -> zip(data, result) } ?: data
                src to updated
            }
            .forEach { (src, feed) ->
                data.put(src, feed)
            }
    }

    /**
     * Combine existing feed data with results.
     *
     * This zipping will retain only articles which are newer than the last crawl date.
     * This means if articles are backdated, there is a possibility for them to be missed.
     *
     */
    private fun zip(data: FeedData, result: FeedResult): FeedData = result
        .takeIf { it.sourceId == data.id }
        ?.let { new ->
            data.copy(
                articles = data.articles + (new.articles
                    .filter { it.timestamp > data.lastChecked }
                    .map {
                        println("Found new article ${it.id}")
                        it.id to it
                    }
                        ),
                lastChecked = System.currentTimeMillis()
            )
        }
        ?: data


}