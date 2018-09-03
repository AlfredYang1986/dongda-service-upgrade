package services

import play.api.mvc.Request
import com.pharbers.jsonapi.model


trait Brick {

    val brick_name: String
    var next_brick: String

    implicit val request: Request[model.RootObject]

    def prepare: Unit

    def exec: Unit

    def done: Option[String]

    def forwardTo(next_brick: String)

    def goback: model.RootObject
}
