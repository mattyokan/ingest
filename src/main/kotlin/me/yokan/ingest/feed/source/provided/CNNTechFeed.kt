package me.yokan.ingest.feed.source.provided

import me.yokan.ingest.feed.source.RSSFeed
import org.springframework.stereotype.Component
import java.net.URL

@Component
class CNNTechFeed : RSSFeed {
    override val id: String get() = "cnn_tech"
    override val endpoint: URL
        get() = URL("http://rss.cnn.com/rss/cnn_tech.rss")

    override val baseUrl: String get() = "www.cnn.com"
}