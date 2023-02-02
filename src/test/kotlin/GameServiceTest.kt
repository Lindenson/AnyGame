import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import wolper.entities.Game
import wolper.dao.GameDAOImplementation


class GameServiceTest {

    val template: R2dbcEntityTemplate = mockk()

    @Test
    fun `should return game by name`() {
        val game = Flux.just(Game(1, "pppp",1234, 0, 0, "tttt", 4321, 0,0, 1))
        every { template.select<Game>(any(), any()) } returns game

        val gameService = GameDAOImplementation(template)
        val gameByName = gameService.getGames("pppp", "tttt")

        var res : Game? = null
        gameByName.doOnNext { res = it }.subscribe()
        assertEquals( 1234, res!!.move1 )    }

    @Test
    fun `should not return unsaved game`() {
        every { template.select<Game>(any(), any()) } returns Flux.empty()

        val gameService = GameDAOImplementation(template)
        val gameByName = gameService.getGames("pppp", "tttt")

        var res : Game? = null
        gameByName.doOnNext { res = it }.subscribe()
        assertTrue { res == null }
    }

    @Test
    fun `should create a valid game `() {
        val game = Game(1, "pppp",1234, 0, 0, "tttt", 4321, 0,0, 1)
        every { template.insert(any<Game>()) } returns Mono.just(game)

        val gameService = GameDAOImplementation(template)
        val gameByName = gameService.createGame("pppp",  "1234", 0, 0, "tttt","4321", 0, 0, "1")

        var res : Game? = null
        gameByName.doOnNext { res = it }.subscribe()
        assertEquals( 1234, res!!.move1 )    }
}