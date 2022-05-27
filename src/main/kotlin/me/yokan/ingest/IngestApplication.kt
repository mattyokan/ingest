package me.yokan.ingest

import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
//import me.yokan.ingest.data.FeedRepository
import me.yokan.ingest.extension.task
import me.yokan.ingest.feed.FeedController
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import kotlin.concurrent.timer
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class IngestApplication(
    private val feedController: FeedController,
//	private val feedRepository: FeedRepository
) : CommandLineRunner {

	override fun run(vararg args: String?) {
		println(" -- Welcome to Ingest --")
//		runBlocking {
//			feedRepository.connect()
//		}
		task(
			name = "query",
			daemon = false,
			initialDelay = 5.seconds,
			period = 15.minutes
		) {
			feedController.queryAll()
		}
	}
}

fun main(args: Array<String>) {
    runApplication<IngestApplication>(*args)
}
