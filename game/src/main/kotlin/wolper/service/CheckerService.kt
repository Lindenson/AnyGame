package wolper.service

interface CheckerService {
    fun countBulls(puzzle: Int, guess: Int): List<Int>
}