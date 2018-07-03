package services.AuthService

import services.Service
import model.steps.{commonerror, commonstep}
import com.pharbers.model.detail.{commonresult, company, user, userdetailresult}

trait AuthService extends Service[user]

case class testStep(override val args : commonresult) extends commonstep {
    override val module: String = "test"
    override val methed: String = "test"

    override def processes(pr: Option[commonresult]): (Option[commonresult], Option[commonerror]) = {
        (Some(userdetailresult("12345", 1, 1,
            Some(user("1234", "alfred yang", "13720200856", 100.1, "atest", 1123, Nil)),
            List(company("5678", "company name pharbers"), company("0825", "company name blackmirror"))
        )), None)
    }
}
