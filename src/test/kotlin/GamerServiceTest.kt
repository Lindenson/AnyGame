import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.core.publisher.Mono
import wolper.dao.GamerDAOImplementation
import wolper.entities.Gamer
import java.time.LocalDateTime


class GamerServiceTest {

    val template: R2dbcEntityTemplate = mockk()

    @Test
    fun `should return gamer by name`() {
        val gamer = Mono.just(Gamer(1, "pppp","1234", LocalDateTime.now()))
        every { template.selectOne<Gamer>(any(), any()) } returns gamer

        val gamerService = GamerDAOImplementation(template)
        val gameByName = gamerService.getGamer("pppp")

        var res : Gamer? = null
        gameByName.doOnNext { res = it }.subscribe()
        assertEquals( "pppp", res!!.name )    }

    @Test
    fun `should not return unsaved gamer`() {
        every { template.selectOne<Gamer>(any(), any()) } returns Mono.empty()

        val gamerService = GamerDAOImplementation(template)
        val gamerByName = gamerService.getGamer("pppp")

        var res : Gamer? = null
        gamerByName.doOnNext { res = it }.subscribe()
        assertTrue { res == null }
    }

    @Test
    fun `should create a valid gamer `() {
        val gamer = Mono.just(Gamer(1, "pppp","1234", LocalDateTime.now()))
        every { template.selectOne<Gamer>(any(), any()) } returns Mono.empty()
        every { template.insert(any<Gamer>()) } returns gamer


        val gamerService = GamerDAOImplementation(template)
        val gamerByName = gamerService.createGamer("pppp")

        var res : Gamer? = null
        gamerByName.doOnNext { res = it }.subscribe()
        assertEquals( "pppp", res!!.name )    }

    @Test
    fun `should not insert double gamer`() {
        val gamer = Mono.just(Gamer(1, "pppp","1234", LocalDateTime.now()))
        every { template.selectOne<Gamer>(any(), any()) } returns gamer
        every { template.insert(any<Gamer>()) } returns gamer

        val gamerService = GamerDAOImplementation(template)
        val gamerByName = gamerService.createGamer("pppp")


        var res : Throwable? = null
        gamerByName.doOnError { res = it }.subscribe()
        assertTrue( res is IllegalArgumentException)
    }
}