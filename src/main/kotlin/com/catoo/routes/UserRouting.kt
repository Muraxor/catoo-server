package com.catoo.routes

import com.catoo.db.dao.DAOFacade
import com.catoo.db.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.userRouting(daoFacade: DAOFacade) {
    routing  {
        route("/user") {
            get("{id}") {
                val id = call.parameters.getOrFail<Int>("id").toInt()
                val user = daoFacade.user(id)

                if (user == null) {
                    call.respond(message = "No users found", status = HttpStatusCode.OK)
                } else {
                    call.respond(user)
                }
            }

            post {
                val user = call.receive<User>()
                daoFacade.addNewUser(user.firstName, user.lastName)
                call.respondText("User created", status = HttpStatusCode.Created)
            }
            delete("{id?}") {

            }
        }
    }
}