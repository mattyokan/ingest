package me.yokan.ingest.feed.source.provided

import me.yokan.ingest.feed.source.RSSFeed
import org.springframework.stereotype.Component
import java.net.URL

@Component
class WSJTechFeed : RSSFeed {
    override val id: String get() = "wsj_technology"
    override val endpoint: URL
        get() = URL("https://feeds.a.dj.com/rss/RSSWSJD.xml")

    override val baseUrl: String get() = "www.wsj.com"
}