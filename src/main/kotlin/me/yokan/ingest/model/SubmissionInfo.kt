package me.yokan.ingest.model

data class SubmissionInfo(
    val dateSubmitted: String,
    val datePublished: String,
    val description: String,
    val authors: List<String>,
    val language: String,
    val sourceDomain: String,
    val submitters: List<String>,
    val tags: List<String>,
    val text: String = description,
    val url: String,


    // Other components inferred but present in data model (https://github.com/responsible-ai-collaborative/aiid/blob/master/site/realm/data_sources/mongodb-atlas/aiidprod/submissions/schema.json)
    val dateDownloaded: String = dateSubmitted,
    val incidentDate: String = datePublished,
    val dateModified: String = dateSubmitted,
)

data class QueuedSubmissions(
    val reports: List<SubmissionInfo>
)