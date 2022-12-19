package com.catoo.routes

import com.catoo.auth.jwt.JWTTokenService
import com.catoo.db.dao.DAOFacade
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.userRouting(daoFacade: DAOFacade) {
    routing {
        route("/user") {
            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val user = daoFacade.userBy(id)

                if (user == null) {
                    call.respond(message = "No users found", status = HttpStatusCode.OK)
                } else {
                    call.respond(user)
                }
            }

            post {
                val params = call.parameters
                // TODO: validate parameters
                val user = daoFacade.addNewUser(
                    params["firstName"].toString(),
                    params["lastName"].toString(),
                    params["password"].toString(),
                )
                if (user == null) {
                    call.respond(
                        message = "Can not create user with these parameters",
                        status = HttpStatusCode.BadRequest
                    )
                } else {
                    val token = JWTTokenService().generate(this@userRouting)

                    call.respond(
                        message = hashMapOf("token" to token),
                        status = HttpStatusCode.Created
                    )
                }
            }
            delete("{id?}") {

            }
        }
    }
}