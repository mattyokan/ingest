package me.yokan.ingest.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import me.yokan.ingest.feed.source.FeedSource
import me.yokan.ingest.model.ArticleData
import me.yokan.ingest.model.FeedData
import org.intellij.lang.annotations.Language
import org.springframework.stereotype.Component
import java.sql.Connection

//@Component
//class FeedRepository {
//
//    var dataSource: HikariDataSource? = null
//
//    private suspend inline fun <T> connection(closure: Connection.() -> T): T? = dataSource?.use { ds ->
//        ds.connection?.use {
//            with(it) {
//                return closure()
//            }
//        }
//    }
//
//    suspend fun connect() {
//        val configHost = System.getenv("INGEST_MYSQL_HOST")
//        val configPort = System.getenv("INGEST_MYSQL_PORT")
//        val configDatabase = System.getenv("INGEST_MYSQL_DB")
//        val configUser = System.getenv("INGEST_MYSQL_USER")
//        val configPass = System.getenv("INGEST_MYSQL_PASS")
//
//        println("Connecting with host $configHost, port $configPort, db $configDatabase")
//
//        val conf = HikariConfig()
//        conf.apply {
//            jdbcUrl = "jdbc:mysql://$configHost:$configPort/$configDatabase"
//            username = configUser
//            password = configPass
//        }
//
//        dataSource = HikariDataSource(conf)
//        connection {
//            println("Connected to MySQL data source successfully!")
//        }
//        createTables()
//    }
//
//    suspend fun createTables() {
//        connection {
//            @Language("MySQL")
//            val feed = """
//                create table if not exists feeds
//                (
//                    id          VARCHAR(128) not null,
//                    lastChecked TIMESTAMP    not null,
//                    constraint feed_pk
//                        primary key (id)
//                );
//            """.trimIndent()
//
//            @Language("MySQL")
//            val article = """
//                create table if not exists articles
//                (
//                    feed_id     VARCHAR(128)       not null,
//                    id          VARCHAR(256)       not null,
//                    timestamp   TIMESTAMP          not null,
//                    author      VARCHAR(128)       not null,
//                    title       VARCHAR(1024)      not null,
//                    description VARCHAR(8192)      not null,
//                    reviewed    BOOL default false not null,
//                    flagged     BOOL               null,
//                    constraint articles_pk
//                        primary key (id),
//                    constraint articles_feeds_id_fk
//                        foreign key (feed_id) references feeds (id)
//                            on update cascade on delete cascade
//                );
//            """.trimIndent()
//
//            listOf(feed, article)
//                .forEach { createText ->
//                    createStatement().use {
//                        it.executeUpdate(createText)
//                    }
//                }
//        }
//    }
//
//    suspend fun retrieve(sources: List<FeedSource>): Map<FeedSource, FeedData> {
//        val res = mutableMapOf<String, ArticleData>()
//
//        connection<Map<FeedSource,FeedData>> {
//            prepareStatement(
//                "SELECT * FROM articles WHERE feed_id in (?)"
//            )
//                .apply {
//                    setArray(1, createArrayOf("varchar", sources.map { it.id }.toTypedArray()))
//                }
//                .executeQuery().use {
//                    while(it.next()) {
//
//                    }
//                    return res
//                }
//        }
//        return res
//    }
//
//}