package me.yokan.ingest.data.source

import me.yokan.ingest.data.FeedDataSource
import me.yokan.ingest.data.ReviewState
import me.yokan.ingest.feed.source.FeedSource
import me.yokan.ingest.model.ArticleData
import me.yokan.ingest.model.FeedData
import me.yokan.ingest.model.ReviewResult
import org.springframework.stereotype.Component



// TODO: Replace with spring data persistent storage of some kind (use spring data...)
@Component
class InMemoryFeed : FeedDataSource {

    private var inMemoryStore = mutableMapOf<FeedSource, FeedData>()

    override suspend fun retrieve(sources: List<FeedSource>): Map<FeedSource, FeedData> = sources.associate { source ->
        inMemoryStore[source]?.let { source to it } ?: (source to FeedData(
            source.id,
            -1L,
            emptyMap()
        ))
    }

    override suspend fun put(source: FeedSource, data: FeedData) {
        inMemoryStore[source] = data
    }

    override fun nextArticle() = inMemoryStore.values
        .asSequence()
        .mapNotNull { feed ->
            feed.articles
                .values
                .sortedBy { it.timestamp }
                .firstOrNull { !it.reviewed }
                ?.let { ReviewResult(feed.id, it)}
        }
        .take(1)
        .firstOrNull()


    override fun retrieveMarked() = inMemoryStore.entries
        .flatMap { (source, data) -> data.articles.values.map { source to it } }
        .filter { (_, data) -> data.flagged == true }

    override fun markReviewed(sourceId: String, id: String, flagged: Boolean) = inMemoryStore.entries
        .find { (source, _) -> source.id == sourceId }
        ?.let { (source, data) ->
            data.articles[id]?.let { article ->
                markReviewed(source, article, flagged)
                ReviewState.Valid
            } ?: ReviewState.UnknownArticle
        } ?: ReviewState.UnknownSource

    override fun articleInfo(sourceId: String, id: String) = inMemoryStore.entries
        .find { (source, _) -> source.id == sourceId }
        ?.let { (source, data) ->
            data.articles[id]
                ?.let { source to it }
        }

    private fun markReviewed(source: FeedSource, articleData: ArticleData, flagged: Boolean) =
        inMemoryStore[source]
            ?.let { data ->
                val articles = (data.articles - articleData.id) + (articleData.id to articleData.copy(
                    reviewed = true,
                    flagged = flagged
                ))
                inMemoryStore[source] = data.copy(articles = articles)
            }

}