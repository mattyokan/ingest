package me.yokan.ingest.extension

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.timer
import kotlin.time.Duration

fun task(
    name: String,
    daemon: Boolean,
    period: Duration,
    initialDelay: Duration = Duration.ZERO,
    closure: suspend () -> Unit
) = timer(
    name = name,
    daemon = daemon,
    initialDelay = initialDelay.inWholeMilliseconds,
    period = period.inWholeMilliseconds
) {
    runBlocking {
        launch {
            closure()
        }
    }
}