package services

import play.api.mvc.Request
import com.pharbers.jsonapi.model
import akka.actor.{Actor, ActorLogging, Props}
import com.pharbers.jsonapi.model.{Error, Errors, RootObject}

object Gateway {
    def prop(implicit request: Request[model.RootObject]): Props = Props(new Gateway)
}

class Gateway(implicit request: Request[model.RootObject]) extends Actor with ActorLogging {
    def receive: PartialFunction[Any, Unit] = {
        case brick: Brick =>
            try {
                brick.prepare
                brick.exec
                brick.done match {
                    case Some(next_brick) => sender ! brick.forwardTo(next_brick)
                    case None => sender ! brick.goback
                }
            } catch {
                case msg: Exception =>
                    println(msg)

                    sender ! RootObject(errors = Some(Seq(
                        Error(
                            id = Some("-1"),
                            status = Some("error"),
                            code = Some("-9999"),
                            title = Some("未知异常"),
                            detail = Some("unknow error")
                        )).asInstanceOf[Errors]))
            }

        case _ => Unit
    }
}
