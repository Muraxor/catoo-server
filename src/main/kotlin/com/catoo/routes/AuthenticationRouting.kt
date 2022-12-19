package com.catoo.routes

import com.catoo.auth.jwt.JWTTokenService
import com.catoo.db.dao.DAOFacade
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authenticationRouting(daoFacade: DAOFacade) {
    routing {
        authenticate("auth-jwt") {
            get("/hello") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }
        post("/login") {
            val params = call.parameters
            val firstName = params["firstName"]
            val password = params["password"]

            if (firstName == null || password == null) {
                call.respond(
                    message = "Invalid firstname or password",
                    status = HttpStatusCode.BadRequest
                )
                return@post
            }

            val user = daoFacade.user(firstName, password)

            if (user == null) {
                call.respond(
                    message = "User not found",
                    status = HttpStatusCode.BadRequest
                )
            } else {
                val token = JWTTokenService().generate(this@authenticationRouting)

                call.respond(
                    message = hashMapOf("token" to token),
                    status = HttpStatusCode.OK
                )
            }
        }
    }
}