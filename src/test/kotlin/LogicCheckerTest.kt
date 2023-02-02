import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import wolper.service.CheckerService


class LogicCheckerTest {
    @Test
    fun `should count bulls`() {
        val checkerServise = CheckerService()
        val countBulls = checkerServise.countBulls(1234, 4321)
        val caws = countBulls[0]
        val bulls = countBulls[1]

        assertEquals(4, bulls)
        assertEquals(0, caws)
    }

    @Test
    fun `should count caws`() {
        val checkerServise = CheckerService()
        val countBulls = checkerServise.countBulls(1234, 1234)
        val caws = countBulls[0]
        val bulls = countBulls[1]

        assertEquals(0, bulls)
        assertEquals(4, caws)
    }

    @Test
    fun `should count nothing`() {
        val checkerServise = CheckerService()
        val countBulls = checkerServise.countBulls(1234, 5678)
        val caws = countBulls[0]
        val bulls = countBulls[1]

        assertEquals(0, bulls)
        assertEquals(0, caws)
    }
}