package wolper.adao

import reactor.core.publisher.Mono
import wolper.formal.domain.AGameState


interface AGameStateDAO<W: AGameState<*, *, *>> {
    fun createGameState(gameState: W, work : (W) -> String) : Mono<W>
    fun getGameState(id: Long, work: (String) -> W): Mono<out W>
    fun updateGameState(id: Long, gameState : W, work : (W) -> String) : Mono<out W>
    fun deleteGameState(id : Long) : Mono<Long>

}