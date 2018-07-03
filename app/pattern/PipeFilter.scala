package pattern

import model.common.timeout
import model.steps.commonstep
import scala.concurrent.duration._
import pattern.manager.SequenceSteps
import com.pharbers.model.detail.commonresult
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, Props}

object PipeFilter {
    def prop(originSender : ActorRef, sequence : SequenceSteps) : Props = Props(new PipeFilter(originSender, sequence))
}

class PipeFilter (originSender : ActorRef, sequence : SequenceSteps) extends Actor with ActorLogging {

    var next : ActorRef = _
    var tmp : Option[Boolean] = None
    var rst : Option[commonresult] = sequence.cr

    val timeOutSchdule: Cancellable = context.system.scheduler.scheduleOnce(5 second, self, new timeout)

    def receive: PartialFunction[Any, Unit] = {
        case cmd : commonstep => dispatchImpl(cmd)
//        case cmd : ParallelMessage => {
//            cancelActor
//            next = context.actorOf(ScatterGatherActor.prop(originSender, msr), "scat")
//            next ! cmd
//        }
        case timeout() =>
            originSender ! new timeout
            cancelActor
        case x : AnyRef =>
            println(x)
            ???
    }

    def dispatchImpl(cmd : commonstep): Boolean = {
        tmp = Some(true)
        cmd.processes(rst) match {
            case (_, Some(err)) =>
                originSender ! err //error(err)
                cancelActor
            case (r @ Some(_), _) =>
                rst = r
                TestLog.print(s"rst = $rst")
                rstReturn
                cancelActor
        }
    }

    def rstReturn : Unit = tmp match {
        case Some(_) => rst match {
            case Some(r) =>
                sequence.steps match {
                    case Nil =>
                        //                        originSender ! result(toJson(r))
                        originSender ! r
                    case head :: tail =>
                        head match {
//                            case p : ParallelMessage => {
//                                next = context.actorOf(ScatterGatherActor.prop(originSender, MessageRoutes(tail, rst)), "scat")
//                                next ! p
//                            }
                            case c : commonstep =>
                                next = context.actorOf(PipeFilter.prop(originSender, SequenceSteps(tail, rst)), "pipe")
                                next ! c
                        }
                    case _ => println("msr error")
                }
            case _ => Unit
        }
        case _ => println("never go here"); Unit
    }

    def cancelActor: Boolean = {
        timeOutSchdule.cancel
        // context.stop(self) 		// 因为后创建的是前创建的子Actor，当父Actor stop的时候，子Actor 也同时Stop，不能进行传递了
    }
}
