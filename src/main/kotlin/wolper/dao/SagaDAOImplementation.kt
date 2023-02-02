package wolper.dao



import jakarta.validation.constraints.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import wolper.entities.Saga


@Service
@Qualifier("bareService")
@Validated
class SagaDAOImplementation(
    private val template: R2dbcEntityTemplate,
) : SagaDAO {


    override fun getSaga(@NotNull player1 : String, @NotNull player2 : String) : Mono<Saga> {
        return template.selectOne<Saga>(
                query(where("player1").`is`(player1).and(where("player2").`is`(player2))),
                Saga::class.java
            )
    }

    override fun createSaga(
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+") player1: String,
        @NotNull @Size(min = 3, max = 20) @Pattern(regexp = "[0-9A-Za-z]+")player2: String): Mono<Saga>
    {
        val newSaga = Saga(null, player1, null, player2, null,
            0, 0, null, 0, null, null, 0)
        return getSaga(player1, player2)
            .flatMap { Mono.error<Saga>(IllegalArgumentException())  }
            .switchIfEmpty { template.insert(newSaga) }
    }

    override fun deleteSaga(@NotNull player1 : String, @NotNull player2 : String) : Mono<Long> {
        return template.delete(
            query(where("player1").`is`(player1).and(where("player2").`is`(player2))),
            Saga::class.java)
    }

    override fun updateSaga(@NotNull saga : Saga): Mono<Saga> {
        return template.update(saga)
    }

}