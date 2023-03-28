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

//    createGameHistory/updateGameHistory(gameHistory)
//    { from  ->  Pair(from.dispositions.map { AMapper.toDespositionDTO(from.gameID, json.desposition, it) },
//                     from.moves.map        { AMapper.toMoveDTO(from.gameID,        json.move,        it)})}

    override fun createGameHistory(gameHistory: W, pairOfMappers : (W) -> Pair<List<AGameDispositionDTO>, List<AGameMoveDTO>> ) : Mono<out W>{
        val (dList, mList) = pairOfMappers(gameHistory)
        return Flux.fromIterable(dList).flatMap { template.insert(it) }.collectList().flatMap {
        Flux.fromIterable(mList).flatMap { template.insert(it) }.collectList() }.flatMap { Mono.just(gameHistory) }
    }

//    getGameHistory(188, {x,y,z-> GameHistory(x,y as List<GameDisposition>,z as List<Move>)},
//                        { AMapper.fromDespositionDTO<GameDisposition>(json.desposition, it)},
//                        { AMapper.fromMoveDTO<Move>(json.move, it)})
    override fun getGameHistory(id: Long, classer : (Long, List<AGameDisposition<*>>, List<AMove<*>>) -> (W), remaperD: (AGameDispositionDTO) -> (AGameDisposition<*>), remaperM: (AGameMoveDTO) -> (AMove<*>)) : Mono <out W> {
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

    override fun updateGameHistory(gameHistory: W, pairOfMappers : (W) -> Pair<List<AGameDispositionDTO>, List<AGameMoveDTO>>) : Mono<out W> {
        val (dList, mList) = pairOfMappers(gameHistory)
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