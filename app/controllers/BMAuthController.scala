package controllers

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import com.pharbers.jsonapi.json.circe.CirceJsonapiSupport
import com.pharbers.jsonapi.model.RootObject
import com.pharbers.model.detail.{company, user}
import play.api.libs.circe._
import play.api.mvc._
import play.api._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import pattern.entry.PlayEntry
import pattern.manager.SequenceSteps
import services.AuthService.testStep
import com.pharbers.model._
import com.pharbers.macros.common.resulting.Resultable
import com.pharbers.macros.common.resulting.Resultable._
import com.pharbers.macros.common.expending.Expandable
import com.pharbers.macros.common.expending.Expandable._
import com.pharbers.model.detail.userdetailresult

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

        val ctest = company("12", "alfred")
        val ctestj = asJsonApi(ctest)
        println(ctestj)

//        val udtest = userdetailresult("123", 1, 1)
//        val udtestj = asJsonApiResult(udtest)
//        println(udtestj)

        val result = asJsonApiResult(reVal.asInstanceOf[userdetailresult])
        Ok(result.asJson)
    }
}
