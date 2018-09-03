package entity

import com.pharbers.macros.api.commonEntity
import com.pharbers.macros.common.connecting.One2OneConn

@One2OneConn[hospdecision]("hospitaldecison")
@One2OneConn[madecision]("managerdecision")
class alldecision extends commonEntity {
    var component_name = ""
    var major: Int = 1
    var minor: Int = 0
}
