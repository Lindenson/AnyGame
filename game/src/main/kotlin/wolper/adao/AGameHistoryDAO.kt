package wolper.adao

import reactor.core.publisher.Mono
import wolper.formal.domain.AGameDisposition
import wolper.formal.domain.AGameHistory
import wolper.formal.domain.AMove
import wolper.formal.dto.AGameDispositionDTO
import wolper.formal.dto.AGameMoveDTO


interface AGameHistoryDAO<W: AGameHistory<*,*>> {

    fun <E : AGameDisposition<*>,T : AMove<*>> createGameHistory(gameHistory: W, remaperD: (E) -> String, remaperM: (T)-> String) : Mono <out W>
    fun <E : AGameDisposition<*>,T : AMove<*>> getGameHistory(id: Long, classer : (Long, List<E>, List<T>) -> (W), remaperD: (AGameDispositionDTO) -> E, remaperM: (AGameMoveDTO) -> T) : Mono <out W>
    fun <E : AGameDisposition<*>,T : AMove<*>> updateGameHistory(gameHistory: W, remaperD: (E) -> String, remaperM: (T)-> String) : Mono <out W>
    fun deleteGameHistory(id : Long) : Mono<Long>

}