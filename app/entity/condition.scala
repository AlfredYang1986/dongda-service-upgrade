package entity

import com.pharbers.macros.api.commonEntity
import com.pharbers.macros.common.connecting.ToStringMacro

@ToStringMacro
case class condition() extends commonEntity {
    var key: String =  ""
    var `val`: String = ""
}
