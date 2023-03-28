import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.QueryTimeoutException
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.core.publisher.Mono
import wolper.dao.GamerDAOImplementation
import wolper.dao.GamerDAOValidation
import wolper.Gamer
import java.time.LocalDateTime


class GamerServiceTest {

    val template: R2dbcEntityTemplate = mockk()

    @Test
    fun `should return gamer by name`() {
        val gamer = Mono.just(Gamer(1, "pppp", "1234", false, LocalDateTime.now()))
        every { template.selectOne<Gamer>(any(), any()) } returns gamer

        val gamerService = GamerDAOImplementation(template)
        val gamerDAOValidation = GamerDAOValidation(gamerService)
        val gameByName = gamerDAOValidation.getGamer("pppp")

        var res: Gamer? = null
        gameByName.doOnNext { res = it }.subscribe()
        assertEquals("pppp", res!!.name)
    }

    @Test
    fun `should not return unsaved gamer`() {
        every { template.selectOne<Gamer>(any(), any()) } returns Mono.empty()

        val gamerService = GamerDAOImplementation(template)
        val gamerDAOValidation = GamerDAOValidation(gamerService)
        val gamerByName = gamerDAOValidation.getGamer("pppp")

        var res: Gamer? = null
        gamerByName.doOnNext { res = it }.subscribe()
        assertTrue { res == null }
    }

    @Test
    fun `should create a valid gamer `() {
        val gamer = Mono.just(Gamer(1, "pppp", "1234", false, LocalDateTime.now()))
        every { template.selectOne<Gamer>(any(), any()) } returns Mono.empty()
        every { template.insert(any<Gamer>()) } returns gamer


        val gamerService = GamerDAOImplementation(template)
        val gamerDAOValidation = GamerDAOValidation(gamerService)
        val gamerByName = gamerDAOValidation.createGamer("pppp")

        var res: Gamer? = null
        gamerByName.doOnNext { res = it }.subscribe()
        assertEquals("pppp", res!!.name)
    }

    @Test
    fun `should not insert double gamer`() {
        val gamer = Mono.just(Gamer(1, "pppp", "1234", false, LocalDateTime.now()))
        every { template.insert(any<Gamer>()) } throws DuplicateKeyException("ppp")

        val gamerService = GamerDAOImplementation(template)
        val gamerDAOValidation = GamerDAOValidation(gamerService)
        val gamerByName = gamerDAOValidation.createGamer("pppp")


        var res: Throwable? = null
        gamerByName.doOnError { res = it }.subscribe()
        assertTrue(res is DuplicateKeyException)
    }

    @Test
    fun `should return throwable on sql error`() {
        every { template.selectOne<Gamer>(any(), any()) } throws QueryTimeoutException("")

        val gamerService = GamerDAOImplementation(template)
        val gamerDAOValidation = GamerDAOValidation(gamerService)
        val gamerByName = gamerDAOValidation.getGamer("pppp")

        var res: Throwable? = null
        gamerByName.doOnError { res = it }.subscribe()
        assertTrue(res is QueryTimeoutException)
    }
}