package pattern

import akka.actor.{Actor, ActorLogging, ActorRef, Terminated}
import model.common.{excute, timeout}
import com.pharbers.model.detail.commonresult
import model.steps.{commonerror, commonstep}
import pattern.manager.SequenceSteps
import play.api.libs.json.Json.toJson

class Gateway extends Actor with ActorLogging {

    var originSender : ActorRef = null
    var next : ActorRef = null

    def receive = {
        case excute(sequence) => {
            originSender = sender
            sequence.steps match {
                case Nil => originSender ! new commonerror(0, "error")
                case head :: tail => {
                    head match {
//                        case p : ParallelMessage => {
//                            next = context.actorOf(ScatterGatherActor.prop(self, MessageRoutes(tail, msr.rst)), "gate")
//                            next ! head
//                        }
                        case c : commonstep => {
                            next = context.actorOf(PipeFilter.prop(self, SequenceSteps(tail, sequence.cr)), "gate")
                            next ! head
                        }
                    }

                    context.watch(next)
                }
            }
        }
        case rst : commonresult => {
            originSender ! rst
            cancelActor
        }
        case err : commonerror => {
            originSender ! err
            cancelActor
        }
        case timeout() => {
            originSender ! toJson("timeout")
            cancelActor
        }
        case Terminated(actorRef) => println("Actor {} terminated", actorRef)
        case _ => Unit
    }

    def cancelActor = {
        context.stop(self)
    }
}
