package wolper.dao


import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import reactor.core.publisher.Mono
import wolper.Gamer

interface GamerDAO {
    fun getGamer(@NotNull player: String): Mono<Gamer>
    fun createGamer(
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player : String,
    ): Mono<Gamer>
    fun getGamers(): Mono<List<String>>
    fun deleteGamer(@NotNull player: String): Mono<Long>
}