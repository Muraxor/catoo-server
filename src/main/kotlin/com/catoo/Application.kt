package com.catoo

import com.catoo.db.dao.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.catoo.plugins.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    configureSecurity()
    configureHTTP()
    configureSockets()
    configureSerialization()
    configureRouting()

    install(ShutDownUrl.ApplicationCallPlugin) {
        shutDownUrl = "/shutdown"
        exitCodeSupplier = { 0 }
    }
}
