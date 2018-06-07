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
import model.user.userdetailresult
import pattern.entry.PlayEntry
import pattern.manager.SequenceSteps
import services.AuthService.testStep

@Singleton
class BMAuthController @Inject()
        (implicit val cc: ControllerComponents, implicit val actorSystem: ActorSystem)
            extends AbstractController(cc) with Circe with CirceJsonapiSupport {

    val entry = PlayEntry()

    def parseJson(jsonString: String) : Json = io.circe.parser.parse(jsonString).right.get
    def decodeJson[T](json: Json)(implicit d: io.circe.Decoder[T]) : T = json.as[T].right.get

    def login = Action(circe.json[RootObject]) { implicit request =>
        import model.request.requestsJsonApiOpt.requestsJsonapiRootObjectReader._
        val tt = fromJsonapi(request.body)
        val reVal = entry.commonExcution(
                SequenceSteps(testStep(tt.reqs.head) :: Nil, None))

        val result = model.user.userdetailJsonApiOpt.userJsonapiRootObjectWriter.toJsonapi(reVal.asInstanceOf[userdetailresult])
        Ok(result.asJson)
    }
}
