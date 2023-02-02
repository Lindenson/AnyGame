package wolper.dao

import jakarta.validation.constraints.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import wolper.entities.Game


@Service
@Qualifier("validService")
class GameDAOValidation(@Qualifier("bareService") private var gameService: GameDAO) : GameDAO {

    override fun getGames(player1: String, player2: String): Flux<Game> {
        return try {
            gameService.getGames(player1, player2)
        }catch( ex : Exception) {
            Flux.error(ex)
        }
    }

    override fun createGame(player1 : String, number1: String, bulls1: Int, caws1: Int, player2: String, number2: String, bulls2:Int, caws2:Int, saga: String): Mono<Game> {
        return try {
            gameService.createGame(player1, number1, bulls1, caws1, player2, number2, bulls2, caws2, saga)
        }catch( ex : Exception) {
            Mono.error(ex)
        }
    }

    override fun deleteGame(player1: String, player2: String): Mono<Long> {
        return try {
            gameService.deleteGame(player1, player2)
        }catch( ex : Exception) {
            Mono.error(ex)
        }
    }
}