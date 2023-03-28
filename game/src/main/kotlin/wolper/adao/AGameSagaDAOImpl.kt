package wolper.adao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import wolper.formal.domain.AGameSaga


@Service
@Qualifier("abstractService")
class AGameSagaDAOImpl(private val template: R2dbcEntityTemplate) : AGameSagaDAO {
    override fun createGameSaga(gameSaga: AGameSaga) : Mono<AGameSaga>{
        return template.insert(gameSaga).map { gameSaga }
    }

    override fun getGameSaga(id: Long) : Mono<AGameSaga> {
        return template.selectOne(Query.query(Criteria.where("game_id").`is`(id)), AGameSaga::class.java)
    }

    override fun updateGameSaga(id: Long, gameSaga: AGameSaga) : Mono<AGameSaga> {
        gameSaga.gameID = id
        return deleteGameSaga(id).flatMap { template.insert(gameSaga) }.map { gameSaga }
    }

    override fun deleteGameSaga(id: Long) : Mono<Long> {
        return template.delete(Query.query(Criteria.where("game_id").`is`(id)), AGameSaga::class.java)
        .flatMap { if (it == 0L) Mono.empty() else Mono.just(it)  }
        .map { id }
    }
}


