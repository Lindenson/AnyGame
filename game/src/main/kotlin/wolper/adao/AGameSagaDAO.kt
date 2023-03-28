package wolper.adao

import reactor.core.publisher.Mono
import wolper.formal.domain.AGameSaga

interface AGameSagaDAO {

    fun createGameSaga(gameState : AGameSaga) : Mono<AGameSaga>
    fun getGameSaga(id : Long) : Mono<AGameSaga>
    fun updateGameSaga(id : Long, gameSaga : AGameSaga) : Mono<AGameSaga>
    fun deleteGameSaga(id : Long) : Mono<Long>

}