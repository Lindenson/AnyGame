package wolper.routers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.*
import wolper.handlers.GameHandler
import wolper.handlers.GamerHandler


@Configuration
@EnableWebFlux
class MainRouter(private val gameHandler : GameHandler, private val gamerHandler : GamerHandler,) {

    @Bean
    fun route() = router {
        "/".nest {
            GET("/getgame", gameHandler::getGames)
            GET("/newgame", gameHandler::createGame)
            GET("/delgame", gameHandler::deleteGame)

            GET("/getgamer", gamerHandler::getGamer)
            GET("/newgamer", gamerHandler::createGamer)
            GET("/getgamers", gamerHandler::getGamers)
            GET("/delgamer", gamerHandler::deleteGamer)
        }
    }



}