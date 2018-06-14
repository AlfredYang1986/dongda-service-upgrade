package model.steps

import com.pharbers.jsonapi.model.{Attribute, Attributes}
import com.pharbers.jsonapi.{JsonapiRelationshipObjectFormat, JsonapiResourceObjectFormat, JsonapiRootObjectFormat}
import com.pharbers.macros.common.expending.Expandable

trait commonresult {
    def asJsonApi[T](x : T)(implicit exTag : Expandable[T]) : Attributes = exTag.toJsonapi(x)
}

trait result[A] extends JsonapiRootObjectFormat[A]
trait resourceresult[A] extends JsonapiResourceObjectFormat[A]

trait connectresult[A] extends JsonapiRelationshipObjectFormat[A]
trait mappingresult[A] extends JsonapiRelationshipObjectFormat[A]
trait listingresult[A] extends JsonapiRelationshipObjectFormat[A]