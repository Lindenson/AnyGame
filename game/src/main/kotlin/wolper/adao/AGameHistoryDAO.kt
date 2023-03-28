package wolper.adao

import reactor.core.publisher.Mono
import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AGameHistory
import wolper.formal.domain.AMove
import wolper.formal.dto.AGameDispositionDTO
import wolper.formal.dto.AGameMoveDTO


interface AGameHistoryDAO<W: AGameHistory<*,*>> {

    fun createGameHistory(gameHistory: W, work : (W) -> Pair<List<AGameDispositionDTO>, List<AGameMoveDTO>> ) : Mono<out W>
    fun getGameHistory(id: Long, classer : (Long, List<AGameDisposition<*>>, List<AMove<*>>) -> (W), remaperD: (AGameDispositionDTO) -> (AGameDisposition<*>), remaperM: (AGameMoveDTO) -> (AMove<*>)) : Mono <out W>
    fun updateGameHistory(gameHistory: W, work : (W) -> Pair<List<AGameDispositionDTO>, List<AGameMoveDTO>>) : Mono<out W>
    fun deleteGameHistory(id : Long) : Mono<Long>

}