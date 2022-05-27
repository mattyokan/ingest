package me.yokan.ingest.parse

import com.chimbori.crux.Crux
import me.yokan.ingest.feed.source.FeedSource
import me.yokan.ingest.model.ArticleData
import me.yokan.ingest.model.SubmissionInfo
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class ParseController {
    private val crux = Crux()

    suspend fun parseContent(source: FeedSource, data: ArticleData) =
        data.source.toHttpUrlOrNull()?.let { crux.extractFrom(it) }
            ?.article
            ?.text()
            ?.let { desc ->
                @Suppress("deprecation")
                val currentDate = Date.from(Instant.now())
                    .toGMTString()
                @Suppress("deprecation")
                val articleDate = Date.from(Instant.ofEpochMilli(data.timestamp))
                    .toGMTString()

                SubmissionInfo(
                    dateSubmitted = currentDate,
                    datePublished = articleDate,
                    description = desc,
                    authors = listOf(data.author),
                    language = "en_us", // todo: not a safe assumption...
                    sourceDomain = source.baseUrl,
                    submitters = listOf("Ingest"),
                    tags = emptyList(),
                    url = data.source.toExternalForm()
                )
            }

}
