package me.yokan.ingest.feed.source.provided

import me.yokan.ingest.feed.source.RSSFeed
import org.springframework.stereotype.Component
import java.net.URL

@Component
class NYTimesTechFeed : RSSFeed {
    override val id: String get() = "nyt_tech"
    override val endpoint: URL
        get() = URL("https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml")

    override val baseUrl: String get() = "www.nytimes.com"
}