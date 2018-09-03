package entity

import com.pharbers.macros.api.commonEntity
import com.pharbers.macros.common.connecting.ToStringMacro

@ToStringMacro
class proposal() extends commonEntity {
    var name = ""
    var desc = ""
    var scenario_id = "none"
}
