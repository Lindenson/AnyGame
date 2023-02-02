package wolper.service

class CheckerService {

    fun countBulls(puzzle : Int, guess : Int) : List <Int> {
        val pl = ints(puzzle)
        val gl = ints(guess)

        var bulls = 0
        var caws = 0

        for (i in 0..3)
            for (j in 0..3){
                if (pl[i] == gl[j])
                   if (i==j)
                      caws++
                   else
                       bulls++
            }
        return listOf(caws, bulls)

    }

    private fun ints(guess: Int): List<Int> {
        val g1: Int = guess / 1000
        val g2: Int = guess / 100 - g1 * 10
        val g3: Int = guess / 10 - g2 * 10 - g1 * 100
        val g4: Int = guess - g3 * 10 - g2 * 100 - g1 * 1000
        return listOf(g1, g2, g3, g4)
    }


}