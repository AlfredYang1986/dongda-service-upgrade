package controllers

import play.api.mvc._
import io.circe.syntax._
import akka.actor.ActorSystem
import play.api.libs.circe.Circe
import javax.inject.{Inject, Singleton}
import com.pharbers.pattern.entry.PlayEntry
import com.pharbers.jsonapi.model.RootObject
import com.pharbers.pattern.manager.SequenceSteps
import com.pharbers.jsonapi.json.circe.CirceJsonapiSupport

@Singleton
class BMAuthController @Inject()(implicit val cc: ControllerComponents, implicit val actorSystem: ActorSystem)
        extends AbstractController(cc) with Circe with CirceJsonapiSupport {

    import entity._
    import com.pharbers.macros._
    import com.pharbers.macros.convert.jsonapi.JsonapiMacro._

    val entry = PlayEntry()

    def login: Action[RootObject] = Action(circe.json[RootObject]) { implicit request =>
        val token = request.headers.get("Authorization").getOrElse("")
        println(request.body)
        println(s"*** token: $token")

        val in_data = formJsonapi[request](request.body)
        phLog(s"in_data = $in_data")

//        val reVal = entry.commonExcution(
//            SequenceSteps(List(
//                testStep(in_data)
//            ))
//        )

        val result = new auth()
        val out_data = toJsonapi(result.asInstanceOf[auth])
        phLog(s"out_data = $out_data")
        Ok(out_data.asJson)
    }

    def proposalLst: Action[RootObject] = Action(circe.json[RootObject]) { implicit request =>

        val in_data = formJsonapi[request](request.body)
        phLog(s"in_data = $in_data")

//        val reVal = entry.commonExcution(
//            SequenceSteps(testStep(in_data) :: Nil, None)
//        )

        val result = new proposal()
        result.name = "关卡一"
        result.desc = "This is proposal first."
        result.scenario_id = "none"
        val result2 = new proposal()
        result2.name = "关卡二"
        result2.desc = "This is proposal second."
        result2.scenario_id = "123"
        val out_data = toJsonapi(result :: result2 :: Nil)
        phLog(s"out_data = $out_data")
        Ok(out_data.asJson)
    }

    def layoutLst: Action[RootObject] = Action(circe.json[RootObject]) { implicit request =>

        val in_data = formJsonapi[request](request.body)
        phLog(s"in_data = $in_data")

//        val reVal = entry.commonExcution(
//            SequenceSteps(testStep(in_data) :: Nil, None)
//        )

        val hospdecision1 = new hospdecision()
        hospdecision1.component_name = "hospital-decision"
        val madecision1 = new madecision()
        madecision1.component_name = "manager-decision"
        val alldecision1 = new alldecision()
        alldecision1.component_name = "hospital-decision"
        alldecision1.hospitaldecison = Some(hospdecision1)
        alldecision1.managerdecision = Some(madecision1)
        val alldecision2 = new alldecision()
        alldecision2.component_name = "manager-decision"
        alldecision2.hospitaldecison = Some(hospdecision1)
        alldecision2.managerdecision = Some(madecision1)
        val out_data = toJsonapi(alldecision1 :: alldecision2 :: Nil)

        phLog(s"out_data = $out_data")
        Ok(out_data.asJson)
    }

    def test: Action[RootObject] = Action(circe.json[RootObject]) { implicit request =>
        val token = request.headers.get("Authorization").getOrElse("")
        println(s"*** token: $token")

        val in_data = formJsonapi[request](request.body)
        phLog(s"in_data = $in_data")

//        val reVal = entry.commonExcution(
//            SequenceSteps(testStep(in_data) :: Nil, None)
//        )

        val result = new auth()
        val out_data = toJsonapi(result.asInstanceOf[auth])
        phLog(s"out_data = $out_data")
        Ok(out_data.asJson)
    }

    def step(step: Long): Action[RootObject] = Action(circe.json[RootObject]) { implicit request =>
        Ok(
            services.PlayEntry().excution(auth_test()).asJson
        )
    }
}
