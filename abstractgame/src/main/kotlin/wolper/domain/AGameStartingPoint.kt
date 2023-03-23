package wolper.domain


//S – staring point DTO
//D – disposition DTO
//T – evaluation DTO

abstract class AGameStartingPoint<S> {
    abstract var pointSize: Int
    abstract var startingPoint: List<S>
}