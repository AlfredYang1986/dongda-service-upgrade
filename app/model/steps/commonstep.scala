package model.steps

abstract class commonstep (val module : String = "", val methed : String = "", val args : String = "") {
    def processes(pr : Option[commonresult]) : (Option[commonresult], Option[commonerror])
}
