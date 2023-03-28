package wolper.routers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.*
import wolper.handlers.GameHandler
import wolper.handlers.GamerHandler


@Configuration
@EnableWebFlux
class MainRouter(private val gameHandler : GameHandler, private val gamerHandler : GamerHandler) {

    @Bean
    fun route() = router {
        "/game".nest {
            GET("/all", gameHandler::getGames)
//            GET("/invite", gameHandler::sendInvite)
//            GET("/respond", gameHandler::respondToInvite)
//            GET("/cancel", gameHandler::cancelGame)
//            GET("/puzzle", gameHandler::createPuzzle)
//            GET("/move", gameHandler::makeAMove)
        }
        "/gamer".nest {
            GET("/get", gamerHandler::getGamer)
            GET("/new", gamerHandler::createGamer)
            GET("/all", gamerHandler::getGamers)
            GET("/del", gamerHandler::deleteGamer)
        }
    }



}