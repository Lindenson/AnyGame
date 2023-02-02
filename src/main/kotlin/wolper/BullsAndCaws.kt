package wolper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BullsAndCaws

fun main(args: Array<String>) {
    runApplication<BullsAndCaws>(*args)
}