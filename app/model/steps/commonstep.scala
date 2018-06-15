package model.steps

import com.pharbers.model.detail.commonresult

trait commonstep {
    val module : String
    val methed : String
    val args : commonresult

    def processes(pr : Option[commonresult]) : (Option[commonresult], Option[commonerror])
}
