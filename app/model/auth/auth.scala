package model.auth

trait auth {
    val id : String
    val method : String
    val secret : Option[String]
}
