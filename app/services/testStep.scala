//package services
//
//import com.pharbers.pattern.entity.commonResult
//import com.pharbers.pattern.steps.commonStep
//
//class testStep(override val args : commonResult) extends commonStep {
//
//}
//case class testStep() extends commonStep {
//    override val module: String = "test"
//    override val methed: String = "test"
//
//    override def processes(pr: Option[commonResult]): (Option[commonResult], Option[commonError]) = {
////        (Some(userdetailresult("12345", 1, 1,
////            Some(user("1234", "alfred yang", "13720200856", 100.1, "atest", 1123, Nil)),
////            List(company("5678", "company name pharbers"), company("0825", "company name blackmirror"))
////        )), None)
//        (Some(new model.user()), None)
//    }
//}