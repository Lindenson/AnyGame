package wolper.dao


import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import reactor.core.publisher.Mono
import wolper.entities.Saga

interface SagaDAO {
    fun getSaga(@NotNull player1: String, @NotNull player2: String): Mono<Saga>
    fun createSaga(
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player1 : String,
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player2 : String,
    ): Mono<Saga>
    fun updateSaga(@NotNull saga : Saga): Mono<Saga>
    fun deleteSaga(@NotNull player1: String, @NotNull player2: String): Mono<Long>
}