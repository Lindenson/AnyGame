package wolper.dao


import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import wolper.entities.Game

interface GameDAO {
    fun getGames(@NotNull player1: String, @NotNull player2: String): Flux<Game>
    fun createGame(
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player1 : String,
        @NotNull @Size(min = 4, max = 4) @Pattern(regexp = "[0-9]{4}") number1 : String,
        @NotNull @Min(0) @Max(4)  bulls1 : Int,
        @NotNull @Min(0) @Max(4) caws1 : Int,
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player2 : String,
        @NotNull @Size(min = 4, max = 4) @Pattern(regexp = "[0-9]{4}")  number2 : String,
        @NotNull @Min(0) @Max(4)  bulls2 : Int,
        @NotNull @Min(0) @Max(4)  caws2 : Int,
        @NotNull @Pattern(regexp = "[0-9]+")  saga : String
    ): Mono<Game>
    fun deleteGame(@NotNull player1: String, @NotNull player2: String): Mono<Long>
}