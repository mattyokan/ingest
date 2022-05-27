package me.yokan.ingest.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component

@Component
class SerializationController {

    val mapper = jacksonObjectMapper()


    fun toJson(obj: Any?) = obj?.let { mapper.writeValueAsString(it) }
}