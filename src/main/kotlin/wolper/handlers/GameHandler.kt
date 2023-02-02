package wolper.handlers

import jakarta.validation.ConstraintViolationException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import wolper.dao.GameDAO
import wolper.entities.Game

@Component
class GameHandler(@Qualifier("validService") private val gameService : GameDAO) {

     fun getGames(request: ServerRequest): Mono<ServerResponse> {
         return request.queryParam("player1")
                .flatMap { pp1 ->
                    request.queryParam("player2").map {pp2 ->
                    gameService.getGames(pp1, pp2)
                        .collectList()
                        .flatMap { if (it.isEmpty()) Mono.empty<List<Game>>() else Mono.just(it) }
                        .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
                        .onErrorResume { errorMessageResolver(it) }
                        .switchIfEmpty { ServerResponse.notFound().build() }
                    }
                }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
     }


    fun createGame(request: ServerRequest): Mono<ServerResponse> {
        val p1 = request.queryParam("player1")
        val p2 = request.queryParam("player2")
        val n1 = request.queryParam("number1")
        val n2 = request.queryParam("number2")
        val s1 = request.queryParam("saga")
        return p1.flatMap { pp1 ->
            p2.flatMap { pp2 ->
                n1.flatMap { nn1 ->
                    n2.flatMap { nn2 ->
                        s1.map { ss1 ->
                            gameService.createGame(pp1, nn1, 0, 0, pp2, nn2, 0, 0, ss1)
                                .flatMap { ServerResponse.ok().bodyValue(it) }
                                .onErrorResume { errorMessageResolver(it) }
                                .switchIfEmpty { ServerResponse.badRequest().bodyValue("Wrong parameters") }
                        }
                    }
                }
            }
        }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
    }

    fun deleteGame(request: ServerRequest): Mono<ServerResponse> {
        return request.queryParam("player1")
            .flatMap { pp1 ->
                request.queryParam("player2").map {pp2 ->
                    gameService.deleteGame(pp1, pp2)
                        .flatMap {
                            if (it == 0L) ServerResponse.notFound().build()
                            else ServerResponse.ok().bodyValue("Hasta luego")

                        }
                        .onErrorResume { errorMessageResolver(it) }
            }
        }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
    }

    private fun errorMessageResolver(ex: Throwable) : Mono<ServerResponse> {
        return when (ex) {
            is ConstraintViolationException -> ServerResponse
                .badRequest().bodyValue("Constrain violation: ${ex.message?.replace("1", "1")}")
            is DataAccessException -> ServerResponse.badRequest().bodyValue("Error in database ${ex.message}")
            else -> ServerResponse.badRequest().bodyValue("Server error ${ex.message}")
        }
    }
}


