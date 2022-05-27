package me.yokan.ingest.feed.source.provided

import me.yokan.ingest.feed.source.RSSFeed
import org.springframework.stereotype.Component
import java.net.URL

@Component
class FoxNewsTechFeed : RSSFeed {
    override val id: String get() = "fox_news_tech"
    override val endpoint: URL
        get() = URL("https://moxie.foxnews.com/feedburner/tech.xml")

    override val baseUrl: String get() = "www.foxnews.com"
}