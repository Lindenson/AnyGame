package wolper.adao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import wolper.formal.domain.AGameState
import wolper.formal.dto.AGameStateDTO


@Service
@Qualifier("abstractService")
class AGameStateDAOImpl<W : AGameState<*, *, *>> (private val template: R2dbcEntityTemplate) : AGameStateDAO<W> {

//    createGameState/updateGameState(gameState)
//    { json.state to it }
    override fun createGameState(gameState: W, work : (W) -> String) : Mono<W> {
        val gs = AGameStateDTO(gameState.gameID, work(gameState))
        return template.insert(gs).map{ gameState }
    }

//    getGameState(388)
//    { json.state from it }
    override fun getGameState(id: Long, work: (String) -> W): Mono<out W> {
        return template.selectOne(Query.query(Criteria.where("game_id").`is`(id)), AGameStateDTO::class.java)
            .map { work (it.state!!) }
    }

    override fun updateGameState(id: Long, gameState : W, work : (W) -> String) : Mono<out W>{
        val gs =  work(gameState)
        return template.update(Query.query(Criteria.where("game_id").`is`(id)), Update.update("state", gs), AGameStateDTO::class.java)
        .flatMap { if (it == 0L) Mono.empty() else Mono.just(it) }
        .map{ gameState }
    }

    override fun deleteGameState(id: Long) : Mono<Long>{
        return template.delete(Query.query(Criteria.where("game_id").`is`(id)), AGameStateDTO::class.java)
        .flatMap { if (it == 0L) Mono.empty() else Mono.just(it) }
        .map { id }
    }
}