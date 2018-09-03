package entity

import com.pharbers.macros.api.commonEntity
import com.pharbers.macros.common.connecting.{One2ManyConn, ToStringMacro}

@One2ManyConn[condition]("conditions")
@ToStringMacro
case class request() extends commonEntity {
    var res: String = ""
}
