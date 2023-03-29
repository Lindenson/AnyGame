package wolper.service

import org.springframework.stereotype.Service
import wolper.formal.domain.AGameStartingPoint
import wolper.formal.domain.AMove
import wolper.formal.service.AGameService


@Service("bareService")
class GameServiceImpl : AGameService<Int, Int, Int, Long> {
    override fun createGame(player: Long): Long {
        TODO("""
            1. Find a player // sure he is free // set busy
            2. Create saga
            3. Create game state            
            4. Find a player // sure he is busy // set free
            5. Return game ID
        """.trimIndent())
    }

    override fun invitePlayer(id: Long, player: Long) {
        TODO("""
            1. Find a game saga // check it // update it
            2. Find Player // sure he is free                                 
            3. Update a game state            
            6. Send message po player            
        """.trimIndent())
    }

    override fun joinGame(id: Long, player: Long) {
        TODO("""            
            1. Find a saga  // check it // update it
            2. Update a player
            3. Update state            
            4. Send message po player  
        """.trimIndent())
    }

    override fun refuseGame(id: Long, player: Long) {
        TODO("""
            1. Find a saga  // check it // update it
            2. Update a players
            3. Send the message to player            
        """.trimIndent())

    }

    override fun setStartingPoint(id: Long, player: Long, start: AGameStartingPoint<Int>) {
        TODO("""
            1. Find a saga  // check it // update it
            2. Update state            
            3. Send the message to player
        """.trimIndent())
    }

    override fun tableIsReady(id: Long): Boolean {
        TODO("""
            1. Find a saga  // check it
        """.trimIndent())
    }

    override fun whosTurn(id: Long): Long {
        TODO("""
            1. Find a saga  // check it
        """.trimIndent())
    }

    override fun getEvaluation(id: Long): Int {
        TODO("""
            1. Find a saga  // check it
            2. Find state
            3. Evaluate if needed 
        """.trimIndent())
    }

    override fun getHistoricMoves(id: Long, player: Long): List<AMove<Int>> {
        TODO("""
            1. Find a saga  // check it
            2. Find game history             
        """.trimIndent())
    }

    override fun isMyTurn(id: Long, player: Long): Boolean {
        TODO("""
            1. Find a saga  // check it
            2. Find game history             
        """.trimIndent())
    }

    override fun makeMove(id: Long, move: Int, player: Long): Int {
        TODO("""
            1. Find a saga  // check it
            2. ...
            3. ...
        """.trimIndent())
    }

    override fun getDispositionForPlayer(id: Long, player: Long): Int {
        TODO("""
            1. Find a saga  // check it
            2. Find game state
            3. Filter and return dispositions
        """.trimIndent())
    }

    override fun cancelGame(id: Long, player: Long) {
        TODO("""
            1. Find a saga  // check it // update it            
        """.trimIndent())
    }
}