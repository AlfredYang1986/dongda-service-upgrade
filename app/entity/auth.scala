package entity

import java.util.UUID

import com.pharbers.macros.api.commonEntity
import com.pharbers.macros.common.connecting._

@ToStringMacro
class auth() extends commonEntity {
    var token: String = UUID.randomUUID().toString
}
