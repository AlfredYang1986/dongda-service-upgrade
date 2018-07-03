package controllers

import play.api.mvc._
import io.circe.syntax._
import play.api.libs.circe._
import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import pattern.entry.PlayEntry
import services.AuthService.testStep
import pattern.manager.SequenceSteps
import com.pharbers.macros.common.resulting.Resultable
import com.pharbers.macros.common.resulting.Resultable._
import com.pharbers.macros.common.expending.Expandable
import com.pharbers.macros.common.expending.Expandable._
import com.pharbers.model._
import model.auth.auth_email
import com.pharbers.jsonapi.model.RootObject
import com.pharbers.model.detail.userdetailresult
import com.pharbers.jsonapi.json.circe.CirceJsonapiSupport

@Singleton
class BMAuthController @Inject()(implicit cc: ControllerComponents, actorSystem: ActorSystem)
            extends AbstractController(cc) with Circe with CirceJsonapiSupport {

    import model.request.asJsonApiRequestOpt.asJsonApiRequest
    import model.request.requestsJsonApiOpt.requestsFromJsonapi
    import model.auth.authEmailJsonApiOpt.authEmailJsonapiRelationReader

    val entry = PlayEntry()

    def login: Action[RootObject] = Action(circe.json[RootObject]) { request =>
        val obj = asJsonApiRequest(request.body)
        TestLog.print(s"obj = $obj")

        val reVal = entry.commonExcution(
            SequenceSteps(testStep(obj.reqs.head) :: Nil, None)
        )
        TestLog.print(s"reVal = $reVal")

        val result = asJsonApiResult(reVal.asInstanceOf[userdetailresult])
        Ok(result.asJson)
    }
}
