package services

trait Service[T] {
    def push(p : T) : Option[String]
    def pop(u : T) : Option[String]
    def update(c : T, u : T) : Option[String]
    def query(c : T) : Option[String]
    def queryMulti(c : T) : Option[String]
}
