package model.steps

import org.zalando.jsonapi.{JsonapiRelationshipObjectFormat, JsonapiResourceObjectFormat, JsonapiRootObjectFormat}

trait commonresult

trait result[A] extends JsonapiRootObjectFormat[A]
trait resourceresult[A] extends JsonapiResourceObjectFormat[A]

trait connectresult[A] extends JsonapiRelationshipObjectFormat[A]
trait mappingresult[A] extends JsonapiRelationshipObjectFormat[A]
trait listingresult[A] extends JsonapiRelationshipObjectFormat[A]