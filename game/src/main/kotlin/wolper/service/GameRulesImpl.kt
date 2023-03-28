package wolper.service

import wolper.domain.GameState
import wolper.formal.domain.AGameSaga
import wolper.formal.domain.AMove
import wolper.formal.service.AGameRules



class GameRulesImpl(state : GameState) : AGameRules<Int, Int, Int, Int, Long>(state) {

    override fun validateStartingPointForPlayer(player: Long) {
        TODO("Not yet implemented")
    }

    override fun validateMove(player: Long, move: AMove<Int>) {
        TODO("Not yet implemented")
    }

    override fun getEvaluation(): Int {
        TODO("Not yet implemented")
    }

    override fun checkGameTimeOut(saga : AGameSaga): Boolean {
        TODO("Not yet implemented")
    }

    override fun checkPlayersTimeOut(saga : AGameSaga, player: Long): Boolean {
        TODO("Not yet implemented")
    }



    override val numbersOfPlayersRequired: Int = 2

    override val playerTimeOutSeconds: Int = 300

    override val gameTimeOutSeconds: Int = 300
}

