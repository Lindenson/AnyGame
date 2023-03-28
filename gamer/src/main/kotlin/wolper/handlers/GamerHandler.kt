package wolper.handlers

import jakarta.validation.ConstraintViolationException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import wolper.dao.GamerDAO

@Component
class GamerHandler(@Qualifier("validService") private val gamerService : GamerDAO) {

     fun getGamer(request: ServerRequest): Mono<ServerResponse> {
         return request.queryParam("name")
                .map { pp1 ->
                    gamerService.getGamer(pp1)
                        .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
                        .onErrorResume { errorMessageResolver(it) }
                        .switchIfEmpty { ServerResponse.notFound().build() }
                }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
     }


    fun createGamer(request: ServerRequest): Mono<ServerResponse> {
        return request.queryParam("name")
                .map {pp1->
                    gamerService.createGamer(pp1)
                            .flatMap { ServerResponse.ok().bodyValue(it) }
                            .onErrorResume { errorMessageResolver(it) }
                            .switchIfEmpty { ServerResponse.badRequest().bodyValue("Wrong parameters") }

                }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
    }

    fun deleteGamer(request: ServerRequest): Mono<ServerResponse> {
        return request.queryParam("name")
            .map {pp1 ->
                    gamerService.deleteGamer(pp1)
                        .flatMap {
                            if (it == 0L) ServerResponse.notFound().build()
                            else ServerResponse.ok().bodyValue("Hasta luego")
                        }
                        .onErrorResume { errorMessageResolver(it) }
            }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
    }


    fun getGamers(request: ServerRequest): Mono<ServerResponse> {
        return gamerService.getFreeGamers()
                .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
                .onErrorResume { errorMessageResolver(it) }
                .switchIfEmpty { ServerResponse.notFound().build() }
    }


    private fun errorMessageResolver(ex: Throwable) : Mono<ServerResponse> {
        return when (ex) {
            is ConstraintViolationException -> ServerResponse.badRequest().bodyValue("Constrain violation: ${ex.message?.replace("1", "1")}")
            is DuplicateKeyException -> ServerResponse.badRequest().bodyValue("Player name duplication")
            is DataAccessException -> ServerResponse.badRequest().bodyValue("Error in database ${ex.message}")
            else -> ServerResponse.badRequest().bodyValue("Server error ${ex.message}")
        }
    }
}


