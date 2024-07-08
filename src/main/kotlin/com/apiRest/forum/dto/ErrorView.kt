package com.apiRest.forum.dto

import org.apache.logging.log4j.message.Message
import java.time.LocalDateTime

data class ErrorView (
    val timestamp: LocalDateTime= LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)
