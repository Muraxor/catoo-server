package com.catoo.auth.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.catoo.extensions.getProperty
import io.ktor.server.application.*
import java.util.*

// TODO: DI
class JWTTokenService {

    fun generate(application: Application): String {
        val token = JWT.create()
            .withAudience(application.getProperty("jwt.audience"))
            .withIssuer(application.getProperty("jwt.issuer"))
            //.withClaim("username", user.firstName)
            .withExpiresAt(Date(System.currentTimeMillis() + 20000))
            .sign(Algorithm.HMAC256(application.getProperty("jwt.secret")))

        return token
    }
}