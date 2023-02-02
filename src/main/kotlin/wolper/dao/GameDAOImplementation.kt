package wolper.dao



import jakarta.validation.constraints.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import wolper.entities.Game

@Service
@Qualifier("bareService")
@Validated
class GameDAOImplementation(
    private val template: R2dbcEntityTemplate,
) : GameDAO {

    override fun getGames(@NotNull player1: String, @NotNull player2: String): Flux<Game> {
        return template.select(
                query(where("player1").`is`(player1).and(where("player2").`is`(player2))),
                Game::class.java
            )
    }

    override fun createGame(
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player1 : String,
        @NotNull @Size(min = 4, max = 4) @Pattern(regexp = "[0-9]{4}") number1 : String,
        @NotNull @Min(0) @Max(4)  bulls1 : Int,
        @NotNull @Min(0) @Max(4) caws1 : Int,
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player2 : String,
        @NotNull @Size(min = 4, max = 4) @Pattern(regexp = "[0-9]{4}")  number2 : String,
        @NotNull @Min(0) @Max(4)  bulls2 : Int,
        @NotNull @Min(0) @Max(4)  caws2 : Int,
        @NotNull @Pattern(regexp = "[0-9]+")  saga : String
    ) : Mono<Game> {

        val newGame = Game(null, player1, number1.toInt(), bulls1, caws1, player2, number2.toInt(), bulls2, caws2, saga.toLong())
        return template.insert(newGame)
    }

    override fun deleteGame(@NotNull player1 : String, @NotNull player2 : String) : Mono<Long> {
        return template.delete(
            query(where("player1").`is`(player1).and(where("player2").`is`(player2))),
            Game::class.java)
    }
}