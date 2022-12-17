package com.catoo.extensions

import io.ktor.server.application.*

fun Application.getProperty(path: String): String = environment.config.property(path).getString()