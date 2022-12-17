package com.catoo.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.catoo.db.models.User
import com.catoo.extensions.getProperty
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.authenticationRouting() {
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
            val user = call.receive<User>()
            // Check username and password
            // ...
            val token = JWT.create()
                .withAudience(this@authenticationRouting.getProperty("jwt.audience"))
                .withIssuer(this@authenticationRouting.getProperty("jwt.issuer"))
                .withClaim("username", user.firstName)
                .withExpiresAt(Date(System.currentTimeMillis() + 20000))
                .sign(Algorithm.HMAC256(this@authenticationRouting.getProperty("jwt.secret")))

            call.respond(hashMapOf("token" to token))
        }
    }
}