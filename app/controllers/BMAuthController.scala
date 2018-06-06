package controllers

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import org.zalando.jsonapi.json.circe.CirceJsonapiSupport
import org.zalando.jsonapi.model.RootObject
import play.api.libs.circe._
import play.api.mvc._
import play.api._

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

@Singleton
class BMAuthController @Inject()
        (cc: ControllerComponents, actorSystem: ActorSystem)
            extends AbstractController(cc) with Circe with CirceJsonapiSupport {

    def parseJson(jsonString: String) : Json = io.circe.parser.parse(jsonString).right.get
    def decodeJson[T](json: Json)(implicit d: io.circe.Decoder[T]) : T = json.as[T].right.get

    def login = Action(circe.json[RootObject]) { implicit request =>
        import model.request.requestsJsonApiOpt.requestsJsonapiRootObjectReader._
        val tt = fromJsonapi(request.body)
        println(tt)
        println(tt.asJson)
        Ok(tt.asJson)
    }
}
