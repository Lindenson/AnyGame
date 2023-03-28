import org.junit.jupiter.api.Test
import wolper.domain.GameDisposition
import wolper.domain.Move
import wolper.domain.GameHistory
import org.junit.jupiter.api.Assertions.assertEquals
import wolper.json.JsonHelpers


class JsonTest {

    @Test
    fun `should serialize properly`() {
        val  zone : GameHistory = GameHistory(
            1L,
            mutableListOf(GameDisposition(2, mutableListOf(1, 2))),
            mutableListOf(Move(1, mutableListOf(1)))
        )

        val jsonHelpers = JsonHelpers()

        val poli = jsonHelpers.history
        val serialized = poli to zone
        val source: GameHistory = poli from serialized

        assertEquals(source, zone)
    }
}


