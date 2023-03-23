package wolper.domain



//S – staring point DTO
//D – disposition DTO
//T – evaluation DTO

abstract class AGameDisposition<D> {
    abstract var distositioSize: Int
    abstract var position: List<D>
}
