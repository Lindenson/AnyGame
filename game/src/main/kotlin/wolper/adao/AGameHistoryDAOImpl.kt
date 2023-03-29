package wolper.adao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AGameHistory
import wolper.formal.domain.AMove
import wolper.formal.dto.AGameDispositionDTO
import wolper.formal.dto.AGameMoveDTO


@Service
@Qualifier("abstractService")
class AGameHistoryDAOImpl<W: AGameHistory<*, *>>(private val template: R2dbcEntityTemplate) : AGameHistoryDAO<W> {

//    createGameHistory<GameDisposition, Move>(gameHistory,
//    { json.desposition to it.disposition!! },
//    { json.move to  it.move!! })

    override fun <E : AGameDisposition<*>,T : AMove<*>> createGameHistory(gameHistory: W,
                                                                          remaperD: (E) -> String,
                                                                          remaperM: (T)-> String) : Mono <out W>
    {
        val dList = gameHistory.dispositions.map { remaperD(it as E) }.map { AGameDispositionDTO(gameHistory.gameID, it) }
        val mList = gameHistory.moves.map { remaperM(it as T) }.map { AGameMoveDTO(gameHistory.gameID, it) }
        return Flux.fromIterable(dList).flatMap { template.insert(it) }.collectList().flatMap {
        Flux.fromIterable(mList).flatMap { template.insert(it) }.collectList() }.flatMap { Mono.just(gameHistory) }
    }

//    getGameHistory<GameDisposition, Move>(188,
//    { x,y,z -> GameHistory(x, y ,z) },
//    { json.desposition from it.disposition!! },
//    { json.move from it.move!! })

    override fun <E : AGameDisposition<*>,T : AMove<*>> getGameHistory(id: Long, classer : (Long, List<E>, List<T>) -> (W),
                                remaperD: (AGameDispositionDTO) -> E,
                                remaperM: (AGameMoveDTO) -> T) : Mono <out W>
    {
        return  template.select(Query.query(Criteria.where("game_id").`is`(id)), AGameDispositionDTO::class.java)
                .map { remaperD(it) }.collectList()
                .flatMap { if (it.isEmpty()) Mono.empty() else Mono.just(it) }
                .flatMap { it1 ->
                    template.select(Query.query(Criteria.where("game_id").`is`(id)), AGameMoveDTO::class.java)
                        .map { remaperM(it) }.collectList()
                        .flatMap { if (it.isEmpty()) Mono.empty() else Mono.just(it) }
                        .map { classer(id, it1, it) }
            }
    }

//    updateGameHistory<GameDisposition, Move>(gameHistory,
//    { json.desposition to it.disposition!! },
//    { json.move to  it.move!! })
    override fun <E : AGameDisposition<*>,T : AMove<*>>  updateGameHistory(gameHistory: W,
                                                                           remaperD: (E) -> String,
                                                                           remaperM: (T)-> String) : Mono <out W>
    {
        val dList = gameHistory.dispositions.map { remaperD(it as E) }.map { AGameDispositionDTO(gameHistory.gameID, it) }
        val mList = gameHistory.moves.map { remaperM(it as T) }.map { AGameMoveDTO(gameHistory.gameID, it) }
        return deleteGameHistory(gameHistory.gameID).flatMap {
            Flux.fromIterable(dList).flatMap { template.insert(it) }.collectList().flatMap {
            Flux.fromIterable(mList).flatMap { template.insert(it) }.collectList() }.flatMap { Mono.just(gameHistory) }
        }
    }

    override fun deleteGameHistory(id: Long) : Mono<Long> {
        return template.delete(Query.query(Criteria.where("game_id").`is`(id)), AGameDispositionDTO::class.java)
        .flatMap { if (it == 0L) Mono.empty() else Mono.just(it) }
        .flatMap { template.delete(Query.query(Criteria.where("game_id").`is`(id)), AGameMoveDTO::class.java)
        .flatMap { if (it == 0L) Mono.empty() else Mono.just(it) }
        }.map { id }
    }
}