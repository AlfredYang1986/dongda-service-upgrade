package controllers

import play.api.mvc._
import io.circe.syntax._
import com.pharbers.macros._
import akka.actor.ActorSystem
import play.api.libs.circe.Circe
import services.AuthService.testStep
import javax.inject.{Inject, Singleton}
import com.pharbers.util.log.phLogTrait._
import com.pharbers.pattern.entry.PlayEntry
import com.pharbers.jsonapi.model.RootObject
import com.pharbers.pattern.manager.SequenceSteps
import com.pharbers.jsonapi.json.circe.CirceJsonapiSupport

@Singleton
class BMAuthController @Inject()(implicit val cc: ControllerComponents, implicit val actorSystem: ActorSystem)
        extends AbstractController(cc) with Circe with CirceJsonapiSupport {

    val entry = PlayEntry()

    def login: Action[RootObject] = Action(circe.json[RootObject]) { implicit request =>
        import model.user._

        val in_data = formJsonapi(request.body)
        phLog(s"in_data = $in_data")

        val reVal = entry.commonExcution(
            SequenceSteps(testStep(in_data) :: Nil, None)
        )

        val out_data = toJsonapi(reVal.asInstanceOf[model.user])
        phLog(s"out_data = $out_data")
        Ok(out_data.asJson)
    }
}
