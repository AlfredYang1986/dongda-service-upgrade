package services.AuthService

import javax.inject.Singleton
import model.steps.{commonerror, commonresult, commonstep}
import model.user.{user, userdetailresult}
import services.Service

trait AuthService extends Service[user]

//@Singleton
//class AuthServiceImpl extends AuthService with commonstep {

case class testStep(override val args : commonresult) extends commonstep {
    override val module: String = "test"
    override val methed: String = "test"

    override def processes(pr: Option[commonresult]): (Option[commonresult], Option[commonerror]) = {
//        (Some(userdetailresult("12345", 1, 1, Some(user("1234", "alfred yang", "13720200856", Nil)))), None)
//        (Some(userdetailresult("12345", 1, 1, Some(user("1234", "alfred yang", "13720200856", "1123", Nil)))), None)
        (Some(userdetailresult("12345", 1, 1, Some(user("1234", "alfred yang", "13720200856", "atest", 1123, Nil)))), None)
    }
}
