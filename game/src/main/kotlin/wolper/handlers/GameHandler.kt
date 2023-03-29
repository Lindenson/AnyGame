package wolper.handlers

import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import wolper.adao.AGameHistoryDAOImpl
import wolper.adao.AGameStateDAO
import wolper.domain.*
import wolper.json.JsonHelpers


@Component
class GameHandler(
    private val gameHistory: AGameStateDAO<GameState>,
    private val json : JsonHelpers
) {

     val logger = LoggerFactory.getLogger(GameHandler::class.java)

     fun getGames(request: ServerRequest): Mono<ServerResponse> {
         val gameState = GameState(6588, GameStartingPoint(2, mutableListOf(1, 772)), GameDisposition(2, mutableListOf(771, 772)), 66)
         val gameHistory1 = GameHistory(188, mutableListOf(GameDisposition(2, mutableListOf(1, 1))), mutableListOf(Move(2, mutableListOf(771, 772))))

         return gameHistory.createGameState(gameState)
         { json.state to it }
         .flatMap { ServerResponse.ok().bodyValue(it) }.onErrorResume { errorMessageResolver(it) }
//
//
//
//        logger.debug("get message!")
//
//         return request.queryParam("player1")
//                .flatMap { pp1 ->
//                    request.queryParam("player2")
//                    .map {pp2 ->
//                        gameService.getGames(pp1, pp2)
//                        .collectList()
//                        .flatMap { if (it.isEmpty()) Mono.empty<List<Game>>() else Mono.just(it) }
//                        .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
//                        .onErrorResume { errorMessageResolver(it) }
//                        .switchIfEmpty { ServerResponse.notFound().build() }
//                    }
//                }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
     }


//    fun sendInvite(request: ServerRequest): Mono<ServerResponse> {
//        return request.queryParam("player")
//            .flatMap { n1 ->
//                request.queryParam("invite")
//                    .map { i2 ->
//                        gameLogic.invite(n1, i2)
//                            .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
//                            .onErrorResume { errorMessageResolver(it) }
//                            .switchIfEmpty { ServerResponse.notFound().build() }
//                    }
//            }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
//    }
//
//    fun respondToInvite(request: ServerRequest): Mono<ServerResponse> {
//        return request.queryParam("player")
//                    .map { p1 ->
//                        gameLogic.confirm(p1)
//                            .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
//                            .onErrorResume { errorMessageResolver(it) }
//                            .switchIfEmpty { ServerResponse.notFound().build() }
//            }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
//    }
//
//    fun cancelGame(request: ServerRequest): Mono<ServerResponse> {
//        return request.queryParam("player")
//            .map { p1 ->
//                gameLogic.cansel(p1)
//                    .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
//                    .onErrorResume { errorMessageResolver(it) }
//                    .switchIfEmpty { ServerResponse.notFound().build() }
//            }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
//    }
//
//    fun createPuzzle(request: ServerRequest): Mono<ServerResponse> {
//        return request.queryParam("player")
//            .flatMap { n1 ->
//                request.queryParam("puzzle")
//                    .map { p1 ->
//                        gameLogic.puzzle(n1, p1)
//                            .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
//                            .onErrorResume { errorMessageResolver(it) }
//                            .switchIfEmpty { ServerResponse.notFound().build() }
//                    }
//            }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
//    }
//
//    fun makeAMove(request: ServerRequest): Mono<ServerResponse> {
//        return request.queryParam("player")
//            .flatMap { n1 ->
//                request.queryParam("move")
//                    .map { m1 ->
//                        gameLogic.move(n1, m1)
//                            .flatMap { ix -> ServerResponse.ok().bodyValue(ix) }
//                            .onErrorResume { errorMessageResolver(it) }
//                            .switchIfEmpty { ServerResponse.notFound().build() }
//                    }
//            }.orElse(ServerResponse.badRequest().bodyValue("Wrong parameters"))
//    }
//


    private fun errorMessageResolver(ex: Throwable) : Mono<ServerResponse> {
        return when (ex) {
            is ConstraintViolationException -> ServerResponse
                .badRequest().bodyValue("Constrain violation: ${ex.message?.replace("1", "1")}")
            is DataAccessException -> ServerResponse.badRequest().bodyValue("Error in database ${ex.message}")
            else -> ServerResponse.badRequest().bodyValue("Server error ${ex.message}")
        }
    }
}


