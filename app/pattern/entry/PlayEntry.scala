package pattern.entry

import play.api.mvc._
import akka.pattern.ask
import akka.util.Timeout

import pattern.Gateway
import model.common.excute
import scala.concurrent.Await
import scala.language.postfixOps
import play.api.libs.json.JsValue
import scala.concurrent.duration._
import akka.actor.{ActorSystem, Props}
import pattern.manager.SequenceSteps
import play.api.libs.Files.TemporaryFile

import com.pharbers.model.detail.commonresult

object PlayEntry {
    def apply()(implicit akkasys : ActorSystem, cc : ControllerComponents) = new PlayEntry()
}

class PlayEntry (implicit akkasys : ActorSystem, cc : ControllerComponents) extends AbstractController(cc) {
    implicit val t: Timeout = Timeout(5 second)

    def commonExcution(msr : SequenceSteps) : commonresult = {
        val act = akkasys.actorOf(Props[Gateway])
        val r = act ? excute(msr)
        Await.result(r.mapTo[commonresult], t.duration)
    }

    def uploadRequestArgs(request : Request[AnyContent])(func : MultipartFormData[TemporaryFile] => JsValue) : Result = {
        try {
            request.body.asMultipartFormData.map { x =>
                Ok(func(x))
            }.getOrElse (BadRequest("Bad Request for input"))
        } catch {
            case _ : Exception => BadRequest("Bad Request for input")
        }
    }
}
