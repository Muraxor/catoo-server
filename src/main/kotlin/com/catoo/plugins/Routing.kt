package com.catoo.plugins

import com.catoo.db.dao.DAOFacade
import com.catoo.db.dao.DAOFacadeImpl
import com.catoo.routes.authenticationRouting
import com.catoo.routes.userRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import kotlinx.coroutines.runBlocking

fun Application.configureRouting() {

    val daoFacade: DAOFacade = DAOFacadeImpl()
    userRouting(daoFacade)
    authenticationRouting(daoFacade)
    routing {
        get {
            call.respondText("Hello, world")
        }
//        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
//            version = "4.15.5"
//        }
//        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml") {
//            codegen = StaticHtmlCodegen()
//        }
    }
}
