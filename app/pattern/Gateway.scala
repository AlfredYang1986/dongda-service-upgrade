package pattern

import pattern.manager.SequenceSteps
import model.common.{excute, timeout}
import play.api.libs.json.Json.toJson
import model.steps.{commonerror, commonstep}
import com.pharbers.model.detail.commonresult
import akka.actor.{Actor, ActorLogging, ActorRef, Terminated}

class Gateway extends Actor with ActorLogging {

    var originSender : ActorRef = _
    var next : ActorRef = _

    def receive: PartialFunction[Any, Unit] = {
        case excute(sequence) =>
            originSender = sender
            sequence.steps match {
                case Nil => originSender ! new commonerror(0, "error")
                case head :: tail =>
                    head match {
//                        case p : ParallelMessage => {
//                            next = context.actorOf(ScatterGatherActor.prop(self, MessageRoutes(tail, msr.rst)), "gate")
//                            next ! head
//                        }
                        case _ : commonstep =>
                            next = context.actorOf(PipeFilter.prop(self, SequenceSteps(tail, sequence.cr)), "gate")
                            next ! head
                    }

                    context.watch(next)
            }
        case rst : commonresult =>
            originSender ! rst
            TestLog.print(s"rst = $rst")
            cancelActor()
        case err : commonerror =>
            originSender ! err
            TestLog.print(s"err = $err")
            cancelActor()
        case timeout() =>
            originSender ! toJson("timeout")
            cancelActor()
        case Terminated(actorRef) => println("Actor {} terminated", actorRef)
        case _ => Unit
    }

    def cancelActor(): Unit = {
        context.stop(self)
    }
}
