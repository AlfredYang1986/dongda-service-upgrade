package services

import play.api.mvc._
import akka.pattern.ask
import akka.util.Timeout
import javax.inject.Inject

import akka.actor.ActorSystem
import scala.concurrent.Await
import scala.language.postfixOps
import play.api.libs.json.JsValue

import scala.concurrent.duration._
import com.pharbers.jsonapi.model
import play.api.libs.Files.TemporaryFile
import com.pharbers.jsonapi.model.RootObject

object PlayEntry {
    def apply()(implicit akkasys: ActorSystem, cc: ControllerComponents) = new PlayEntry()
}

class PlayEntry @Inject()(implicit akkasys: ActorSystem, cc: ControllerComponents) extends AbstractController(cc) {
    implicit val t: Timeout = Timeout(5 second)

    def excution(brick: Brick)(implicit request: Request[model.RootObject]): RootObject = {
        val act = akkasys.actorOf(Gateway.prop)
        val r = act ? brick
        Await.result(r.mapTo[RootObject], t.duration)
    }

    def uploadRequestArgs(request: Request[AnyContent])(func: MultipartFormData[TemporaryFile] => JsValue): Result = {
        try {
            request.body.asMultipartFormData.map { x =>
                Ok(func(x))
            }.getOrElse(BadRequest("Bad Request for input"))
        } catch {
            case _: Exception => BadRequest("Bad Request for input")
        }
    }
}